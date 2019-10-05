package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.*;
import com.bucketdev.betapp.dto.TournamentDTO;
import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.exception.group.GroupsNotFoundException;
import com.bucketdev.betapp.exception.groupParticipant.GroupParticipantsNotSufficient;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentWrongStageException;
import com.bucketdev.betapp.exception.tournamentSettings.TournamentSettingsNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.*;
import com.bucketdev.betapp.service.MatchParticipantsService;
import com.bucketdev.betapp.service.TournamentService;
import com.bucketdev.betapp.service.TournamentSettingsService;
import com.bucketdev.betapp.type.PlayoffStage;
import com.bucketdev.betapp.type.TournamentStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private TournamentSettingsRepository tournamentSettingsRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Autowired
    private MatchParticipantsService matchParticipantsService;

    @Autowired
    private TournamentSettingsService tournamentSettingsService;

    public TournamentDTO save(TournamentDTO dto) {
        Tournament tournament = new Tournament();
        if(dto.getId() > 0) {
            Optional<Tournament> tournamentOptional = repository.findById(dto.getId());
            if(!tournamentOptional.isPresent())
                throw new TournamentNotFoundException("id: " + dto.getId());
            tournament = tournamentOptional.get();
        } else {
            Optional<User> optionalUser = userRepository.findById(dto.getUserCreationId());
            if(!optionalUser.isPresent()) {
                throw new UserNotFoundException("id: " + dto.getUserCreationId());
            }
            tournament.setUid(UUID.randomUUID().toString());
            tournament.setCreationDate(Calendar.getInstance());
            tournament.setUserCreation(optionalUser.get());
            tournament.setTournamentStage(TournamentStage.NEW_TOURNAMENT);
        }
        tournament.setValuesFromDTO(dto);
        tournament = repository.save(tournament);


        if (dto.getId() == 0) {
            //add the creation user to the tournament by default
            Participant participant = new Participant();
            participant.setTournament(tournament);
            participant.setUser(tournament.getUserCreation());
            participantRepository.save(participant);

            //create the default tournament settings
            TournamentSettings tournamentSettings = new TournamentSettings();
            tournamentSettings.setTournament(tournament);
            tournamentSettingsRepository.save(tournamentSettings);

            if (dto.isTournamentGroups()) {
                //Create first default group
                Group group = new Group('A');
                group.setTournament(tournament);
                groupRepository.save(group);
            }
        }

        return tournament.toDTO();
    }

    @Override
    public UserDTO addParticipant(long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        if(!optionalUser.isPresent())
            throw  new UserNotFoundException("id: " + userDTO.getId());
        User user = optionalUser.get();

        Optional<Tournament> optionalTournament = repository.findById(id);
        if(!optionalTournament.isPresent())
            throw new TournamentNotFoundException("id: " + id);
        Tournament tournament = optionalTournament.get();

        Participant participant = new Participant();
        participant.setUser(user);
        participant.setTournament(tournament);
        participantRepository.save(participant);

        return user.toDTO();
    }

    @Override
    public TournamentDTO updatePhotoUrl(long id, TournamentDTO dto) {
        Optional<Tournament> optionalTournament = repository.findById(id);
        if (!optionalTournament.isPresent())
            throw new TournamentNotFoundException("id: " + id);
        Tournament tournament = optionalTournament.get();
        tournament.setPhotoUrl(dto.getPhotoUrl());
        return repository.save(tournament).toDTO();
    }

    @Override
    @Transactional
    public TournamentDTO updateTournamentStage(TournamentDTO dto) {
        Optional<Tournament> optionalTournament = repository.findById(dto.getId());
        if(!optionalTournament.isPresent())
            throw new TournamentNotFoundException("id:" + dto.getId());
        Tournament tournament = optionalTournament.get();
        if (!isValidStage(tournament.getTournamentStage(), dto.getTournamentStage()))
            throw new TournamentWrongStageException("from: " + tournament.getTournamentStage() + ", to: " + dto.getTournamentStage());
        TournamentSettings tournamentSettings =
                tournamentSettingsRepository.findByTournamentUid(dto.getUid());
        if (tournamentSettings == null)
            throw new TournamentSettingsNotFoundException("tournament uid: " + dto.getUid());
        if (tournament.isTournamentTeams()) {
            // TODO create matches based on teams
        } else {
            if (dto.getTournamentStage().equals(TournamentStage.GROUP_STAGE)) {
                groupsStageMatches(tournament, tournamentSettings.isGroupRoundTrip());
            } else if (dto.getTournamentStage().equals(TournamentStage.FINALS_STAGE)){
                assignPlayoffGroup(tournamentSettings, tournament.getUid());
                PlayoffStage playoffStage = tournamentSettings.getPlayoffStage();
                switch (playoffStage) {
                    case EIGHTH_FINALS:
                        playoffsStageMatches(tournament, tournamentSettings.isEightFinalsRoundTrip(), playoffStage);
                        break;
                    case QUARTER_FINALS:
                        playoffsStageMatches(tournament, tournamentSettings.isQuarterFinalsRoundTrip(), playoffStage);
                        break;
                    case SEMIFINALS:
                        playoffsStageMatches(tournament, tournamentSettings.isSemiFinalsRoundTrip(), playoffStage);
                        break;
                    case FINALS:
                        playoffsStageMatches(tournament, tournamentSettings.isFinalRoundTrip(), playoffStage);
                        break;
                }
            }
        }
        tournament.setTournamentStage(dto.getTournamentStage());
        return repository.save(tournament).toDTO();
    }

    private boolean isValidStage(TournamentStage from, TournamentStage to) {
        switch (from) {
            case NEW_TOURNAMENT:
                return to.equals(TournamentStage.GROUP_STAGE) ||
                        to.equals(TournamentStage.FINALS_STAGE);
            case GROUP_STAGE:
                return to.equals(TournamentStage.FINALS_STAGE);
            case FINALS_STAGE:
                return to.equals(TournamentStage.FINISHED_TOURNAMENT);
            case FINISHED_TOURNAMENT:
                return false;
            default:
                return true;
        }
    }

    private void assignPlayoffGroup(TournamentSettings tournamentSettings, String uid) {
        // Validate every group has at least the first n participants to be on the playoffs
        List<Group> finalists = groupRepository.findAllByTournamentUid(uid).stream().peek((group -> {
            if (group.getGroupParticipants().size() < tournamentSettings.getFirst())
                throw new GroupParticipantsNotSufficient("groupId: " + group.getId());
        })).collect(Collectors.toList());
        //Get the groups with playoffStage column != null
        List<Group> finalGroups = groupRepository
                .findAllPlayoffsByTournamentUidAndPlayoffStage(uid, tournamentSettings.getPlayoffStage());
        //Create them if they're not created yet
        if (finalGroups == null || finalGroups.size() == 0) {
            tournamentSettingsService.generateFinalsGroups(tournamentSettings);
            finalGroups = groupRepository
                    .findAllPlayoffsByTournamentUidAndPlayoffStage(uid, tournamentSettings.getPlayoffStage());
        }
        int idxFirstGroup = 0;
        int idxLastGroup = finalists.size() - 1;
        int idxPlayoffGroup = 0;
        List<GroupParticipant> newFinalists = new ArrayList<>();
        do {
            int idxFirstPlace = 0;
            int idxLastPlace = tournamentSettings.getFirst() - 1;
            Group groupA = finalists.get(idxFirstGroup);
            Group groupB = finalists.get(idxLastGroup);
            // if it is the same group, then the number of groups was an odd number
            while (groupA.equals(groupB) ? idxFirstPlace < idxLastPlace : idxFirstPlace < tournamentSettings.getFirst()) {
                Group finalGroup = finalGroups.get(idxPlayoffGroup);
                newFinalists.add(
                    new GroupParticipant(finalGroup,
                        groupA.getGroupParticipants().get(idxFirstPlace).getUser(), 0));
                newFinalists.add(
                    new GroupParticipant(finalGroup,
                        groupB.getGroupParticipants().get(idxLastPlace).getUser(), 0));
                idxPlayoffGroup++;
                idxFirstPlace++;
                idxLastPlace--;
            }
            idxFirstGroup++;
            idxLastGroup--;
        } while (idxFirstGroup < idxLastGroup);
        groupParticipantRepository.saveAll(newFinalists);
    }

    private void groupsStageMatches(Tournament tournament, boolean roundTrip) {
        List<Group> groups = groupRepository.findAllByTournamentUid(tournament.getUid());
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            if (group.getGroupParticipants().size() < 2)
                throw new GroupParticipantsNotSufficient("groupId: " + group.getId());
            matchParticipantsService.calculateMatches(group, roundTrip, null);
        }
    }

    private void playoffsStageMatches(Tournament tournament, boolean roundTrip, PlayoffStage playoffStage) {
        List<Group> groups = groupRepository.findAllPlayoffsByTournamentUidAndPlayoffStage(tournament.getUid(), playoffStage);
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            if (group.getGroupParticipants().size() < 2)
                throw new GroupParticipantsNotSufficient("groupId: " + group.getId());
            matchParticipantsService.calculateMatches(group, roundTrip, playoffStage);
        }
    }

    @Override
    public void deleteTournament(String uid) {
        Tournament tournament = repository.findByUid(uid);
        repository.delete(tournament);
    }
}
