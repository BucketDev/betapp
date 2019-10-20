package com.bucketdev.betapp.service.tournament.impl;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.domain.group.GroupParticipant;
import com.bucketdev.betapp.domain.group.GroupTeam;
import com.bucketdev.betapp.domain.match.Participant;
import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.domain.tournament.TournamentSettings;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.tournament.TournamentDTO;
import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.exception.group.GroupsNotFoundException;
import com.bucketdev.betapp.exception.group.GroupParticipantsNotSufficient;
import com.bucketdev.betapp.exception.group.GroupTeamsNotSufficient;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentWrongStageException;
import com.bucketdev.betapp.exception.tournament.TournamentSettingsNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.group.GroupParticipantRepository;
import com.bucketdev.betapp.repository.group.GroupRepository;
import com.bucketdev.betapp.repository.group.GroupTeamRepository;
import com.bucketdev.betapp.repository.match.ParticipantRepository;
import com.bucketdev.betapp.repository.tournament.TournamentRepository;
import com.bucketdev.betapp.repository.tournament.TournamentSettingsRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.match.MatchParticipantsService;
import com.bucketdev.betapp.service.match.MatchTeamsService;
import com.bucketdev.betapp.service.notification.NotificationService;
import com.bucketdev.betapp.service.tournament.TournamentService;
import com.bucketdev.betapp.service.tournament.TournamentSettingsService;
import com.bucketdev.betapp.type.NotificationType;
import com.bucketdev.betapp.type.PlayoffStage;
import com.bucketdev.betapp.type.TournamentPrivacy;
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
    private GroupTeamRepository groupTeamRepository;

    @Autowired
    private MatchParticipantsService matchParticipantsService;

    @Autowired
    private MatchTeamsService matchTeamsService;

    @Autowired
    private TournamentSettingsService tournamentSettingsService;

    @Autowired
    private NotificationService notificationService;

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
            if (!tournament.getTournamentPrivacy().equals(TournamentPrivacy.SECRET))
                notificationService.create(NotificationType.NEW_TOURNAMENT, optionalUser.get(), tournament);
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
        TournamentStage newTournamentStage = dto.getTournamentStage();
        if (!isValidStage(tournament.getTournamentStage(), newTournamentStage))
            throw new TournamentWrongStageException("from: " + tournament.getTournamentStage() + ", to: " + newTournamentStage);
        TournamentSettings tournamentSettings =
                tournamentSettingsRepository.findByTournamentUid(dto.getUid());
        if (tournamentSettings == null)
            throw new TournamentSettingsNotFoundException("tournament uid: " + dto.getUid());
        if (tournament.isTournamentTeams()) {
            if (newTournamentStage.equals(TournamentStage.GROUP_STAGE)) {
                groupStageTeamMatches(tournament, tournamentSettings.isGroupRoundTrip());
            } else if (newTournamentStage.equals(TournamentStage.PLAYOFF_STAGE)) {
                assignPlayoffTeams(tournamentSettings, tournament.getUid());
                PlayoffStage playoffStage = tournamentSettings.getPlayoffStage();
                switch (playoffStage) {
                    case EIGHTH_FINALS:
                        playoffStageTeamMatches(tournament, tournamentSettings.isEightFinalsRoundTrip(), playoffStage);
                        break;
                    case QUARTER_FINALS:
                        playoffStageTeamMatches(tournament, tournamentSettings.isQuarterFinalsRoundTrip(), playoffStage);
                        break;
                    case SEMIFINALS:
                        playoffStageTeamMatches(tournament, tournamentSettings.isSemiFinalsRoundTrip(), playoffStage);
                        break;
                    case FINALS:
                        playoffStageTeamMatches(tournament, tournamentSettings.isFinalRoundTrip(), playoffStage);
                        break;
                }
            }
        } else {
            if (newTournamentStage.equals(TournamentStage.GROUP_STAGE)) {
                groupStageParticipantMatches(tournament, tournamentSettings.isGroupRoundTrip());
            } else if (newTournamentStage.equals(TournamentStage.PLAYOFF_STAGE)){
                assignPlayoffParticipants(tournamentSettings, tournament.getUid());
                PlayoffStage playoffStage = tournamentSettings.getPlayoffStage();
                switch (playoffStage) {
                    case EIGHTH_FINALS:
                        playoffStageParticipantMatches(tournament, tournamentSettings.isEightFinalsRoundTrip(), playoffStage);
                        break;
                    case QUARTER_FINALS:
                        playoffStageParticipantMatches(tournament, tournamentSettings.isQuarterFinalsRoundTrip(), playoffStage);
                        break;
                    case SEMIFINALS:
                        playoffStageParticipantMatches(tournament, tournamentSettings.isSemiFinalsRoundTrip(), playoffStage);
                        break;
                    case FINALS:
                        playoffStageParticipantMatches(tournament, tournamentSettings.isFinalRoundTrip(), playoffStage);
                        break;
                }
            }
        }
        tournament.setTournamentStage(newTournamentStage);
        return repository.save(tournament).toDTO();
    }

    private boolean isValidStage(TournamentStage from, TournamentStage to) {
        switch (from) {
            case NEW_TOURNAMENT:
                return to.equals(TournamentStage.GROUP_STAGE) ||
                        to.equals(TournamentStage.PLAYOFF_STAGE);
            case GROUP_STAGE:
                return to.equals(TournamentStage.PLAYOFF_STAGE);
            case PLAYOFF_STAGE:
                return to.equals(TournamentStage.FINISHED_TOURNAMENT);
            case FINISHED_TOURNAMENT:
                return false;
            default:
                return true;
        }
    }

    private void groupStageParticipantMatches(Tournament tournament, boolean roundTrip) {
        List<Group> groups = groupRepository.findAllByTournamentUid(tournament.getUid());
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            if (group.getGroupParticipants().size() < 2)
                throw new GroupParticipantsNotSufficient("groupId: " + group.getId());
            matchParticipantsService.calculateMatches(group, roundTrip, null);
        }
    }

    private void groupStageTeamMatches(Tournament tournament, boolean roundTrip) {
        List<Group> groups = groupRepository.findAllByTournamentUid(tournament.getUid());
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            if (group.getGroupTeams().size() < 2)
                throw new GroupTeamsNotSufficient("groupId: " + group.getId());
            matchTeamsService.calculateMatches(group, roundTrip, null);
        }
    }

    private void assignPlayoffParticipants(TournamentSettings tournamentSettings, String uid) {
        // Validate every group has at least the first n participants to be on the playoffs
        // TODO validate possible ties
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

    private void playoffStageParticipantMatches(Tournament tournament, boolean roundTrip, PlayoffStage playoffStage) {
        List<Group> groups = groupRepository.findAllPlayoffsByTournamentUidAndPlayoffStage(tournament.getUid(), playoffStage);
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            if (group.getGroupParticipants().size() < 2)
                throw new GroupParticipantsNotSufficient("groupId: " + group.getId());
            matchParticipantsService.calculateMatches(group, roundTrip, playoffStage);
        }
    }

    private void assignPlayoffTeams(TournamentSettings tournamentSettings, String uid) {
        // Validate every group has at least the first n participants to be on the playoffs
        // TODO validate possible ties
        List<Group> finalists = groupRepository.findAllByTournamentUid(uid).stream().peek((group -> {
            if (group.getGroupTeams().size() < tournamentSettings.getFirst())
                throw new GroupTeamsNotSufficient("groupId: " + group.getId());
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
        List<GroupTeam> newFinalists = new ArrayList<>();
        do {
            int idxFirstPlace = 0;
            int idxLastPlace = tournamentSettings.getFirst() - 1;
            Group groupA = finalists.get(idxFirstGroup);
            Group groupB = finalists.get(idxLastGroup);
            // if it is the same group, then the number of groups was an odd number
            while (groupA.equals(groupB) ? idxFirstPlace < idxLastPlace : idxFirstPlace < tournamentSettings.getFirst()) {
                Group finalGroup = finalGroups.get(idxPlayoffGroup);
                newFinalists.add(
                        new GroupTeam(finalGroup,
                                groupA.getGroupTeams().get(idxFirstPlace).getTeam(), 0));
                newFinalists.add(
                        new GroupTeam(finalGroup,
                                groupB.getGroupTeams().get(idxLastPlace).getTeam(), 0));
                idxPlayoffGroup++;
                idxFirstPlace++;
                idxLastPlace--;
            }
            idxFirstGroup++;
            idxLastGroup--;
        } while (idxFirstGroup < idxLastGroup);
        groupTeamRepository.saveAll(newFinalists);
    }

    private void playoffStageTeamMatches(Tournament tournament, boolean roundTrip, PlayoffStage playoffStage) {
        List<Group> groups = groupRepository.findAllPlayoffsByTournamentUidAndPlayoffStage(tournament.getUid(), playoffStage);
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            if (group.getGroupTeams().size() < 2)
                throw new GroupTeamsNotSufficient("groupId: " + group.getId());
            matchTeamsService.calculateMatches(group, roundTrip, playoffStage);
        }
    }

    @Override
    public void deleteTournament(String uid) {
        Tournament tournament = repository.findByUid(uid);
        repository.delete(tournament);
    }
}
