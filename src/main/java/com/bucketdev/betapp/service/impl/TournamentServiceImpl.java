package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.*;
import com.bucketdev.betapp.dto.GroupDTO;
import com.bucketdev.betapp.dto.TournamentDTO;
import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.exception.group.GroupsNotFoundException;
import com.bucketdev.betapp.exception.groupParticipant.GroupParticipantsNotSufficient;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentWrongStageException;
import com.bucketdev.betapp.exception.tournamentSettings.TournamentSettingsNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.*;
import com.bucketdev.betapp.service.TournamentService;
import com.bucketdev.betapp.type.TournamentStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private MatchParticipantsRepository matchParticipantsRepository;

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
    public TournamentDTO updateTournamentStage(TournamentDTO dto) {
        Optional<Tournament> optionalTournament = repository.findById(dto.getId());
        if(!optionalTournament.isPresent())
            throw new UserNotFoundException("id:" + dto.getId());
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
            if (tournament.isTournamentGroups() &&
                    dto.getTournamentStage().equals(TournamentStage.GROUP_STAGE)) {
                if (!tournamentSettings.isGroupRoundTrip()) {
                    oneTripMatches(tournament);
                } else {
                    // TODO create round trip group matches
                }
            } else {
                // TODO create final matches
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

    private void oneTripMatches(Tournament tournament) {
        List<Group> groups = groupRepository.findAllByTournamentUidOrderByName(tournament.getUid());
        if (groups == null || groups.size() == 0)
            throw new GroupsNotFoundException("tournamentUid: " + tournament.getUid());
        for (Group group : groups) {
            List<GroupParticipant> groupParticipants = group.getGroupParticipants();
            if (groupParticipants.size() < 2)
                throw new GroupParticipantsNotSufficient("groupId: " + group.getId());
            int arrayLikeSize = groupParticipants.size() - 1;
            for (int i = 0; i < arrayLikeSize; i++) {
                for (int j = i + 1; j <= arrayLikeSize; j++) {
                    MatchParticipants matchParticipants = new MatchParticipants();
                    matchParticipants.setTournament(tournament);
                    matchParticipants.setGroupParticipantAway(groupParticipants.get(j));
                    matchParticipants.setGroupParticipantHome(groupParticipants.get(i));

                    matchParticipantsRepository.save(matchParticipants);
                }
            }
        }
    }
}
