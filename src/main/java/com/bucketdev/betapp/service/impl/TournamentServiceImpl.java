package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.*;
import com.bucketdev.betapp.dto.TournamentDTO;
import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.*;
import com.bucketdev.betapp.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

/**
 * @author rodrigo.loyola
 */
@Component
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantsRepository participantsRepository;

    @Autowired
    private TournamentSettingsRepository tournamentSettingsRepository;

    @Autowired
    private GroupRepository groupRepository;

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
        }
        tournament.setValuesFromDTO(dto);
        tournament = repository.save(tournament);


        if (dto.getId() == 0) {
            //add the creation user to the tournament by default
            Participants participants = new Participants();
            participants.setTournament(tournament);
            participants.setUser(tournament.getUserCreation());
            participantsRepository.save(participants);

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

        Participants participants = new Participants();
        participants.setUser(user);
        participants.setTournament(tournament);
        participantsRepository.save(participants);

        return user.toDTO();
    }

}
