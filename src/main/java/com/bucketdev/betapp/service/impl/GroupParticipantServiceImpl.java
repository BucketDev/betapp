package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.Group;
import com.bucketdev.betapp.domain.GroupParticipant;
import com.bucketdev.betapp.domain.Tournament;
import com.bucketdev.betapp.domain.User;
import com.bucketdev.betapp.dto.GroupParticipantDTO;
import com.bucketdev.betapp.exception.group.GroupNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.GroupParticipantRepository;
import com.bucketdev.betapp.repository.GroupRepository;
import com.bucketdev.betapp.repository.TournamentRepository;
import com.bucketdev.betapp.repository.UserRepository;
import com.bucketdev.betapp.service.GroupParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class GroupParticipantServiceImpl implements GroupParticipantService {

    @Autowired
    private GroupParticipantRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public GroupParticipantDTO save(GroupParticipantDTO groupParticipantDTO) {
        Optional<Group> groupOptional = groupRepository.findById(groupParticipantDTO.getGroupId());
        if (!groupOptional.isPresent())
            throw new GroupNotFoundException("id: " + groupParticipantDTO.getGroupId());
        Group group = groupOptional.get();
        Optional<User> userOptional = userRepository.findById(groupParticipantDTO.getUser().getId());
        if (!userOptional.isPresent())
            throw new UserNotFoundException("id: " + groupParticipantDTO.getUser().getId());
        User user = userOptional.get();
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(groupParticipantDTO.getTournamentId());
        if (!tournamentOptional.isPresent())
            throw new TournamentNotFoundException("id: " + groupParticipantDTO.getTournamentId());
        Tournament tournament = tournamentOptional.get();

        GroupParticipant groupParticipant = new GroupParticipant();
        groupParticipant.setGroup(group);
        groupParticipant.setTournament(tournament);
        groupParticipant.setUser(user);
        groupParticipant.setValuesFromDTO(groupParticipantDTO);

        return repository.save(groupParticipant).toDTO();
    }

    @Override
    public List<GroupParticipantDTO> findByTournamentId(long tournamentId) {
        return repository.findByTournamentId(tournamentId).stream()
                .map(GroupParticipant::toDTO).collect(Collectors.toList());
    }

}
