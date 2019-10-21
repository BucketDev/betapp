package com.bucketdev.betapp.service.group.impl;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.domain.group.GroupParticipant;
import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.group.GroupParticipantDTO;
import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.exception.group.GroupNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.group.GroupParticipantRepository;
import com.bucketdev.betapp.repository.group.GroupRepository;
import com.bucketdev.betapp.repository.tournament.TournamentRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.group.GroupParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

        GroupParticipant groupParticipant = new GroupParticipant(group, user, 0);

        return repository.save(groupParticipant).toDTO();
    }

    @Override
    @Transactional
    public List<GroupParticipantDTO> saveByGroupId(long groupId, List<UserDTO> users) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent())
            throw new GroupNotFoundException("id: " + groupId);
        Group group = groupOptional.get();
        List<GroupParticipant> groupParticipants = new ArrayList<>();

        users.forEach(userDTO -> {
            Optional<User> userOptional = userRepository.findById(userDTO.getId());
            if (!userOptional.isPresent())
                throw new UserNotFoundException("id: " + userDTO.getId());
            User user = userOptional.get();

            GroupParticipant groupParticipant = new GroupParticipant(group, user, 0);
            groupParticipants.add(groupParticipant);
        });

        return repository.saveAll(groupParticipants).stream().map(GroupParticipant::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GroupParticipantDTO> findByTournamentId(long tournamentId) {
        return repository.findAllByTournamentId(tournamentId).stream()
                .map(GroupParticipant::toDTO).collect(Collectors.toList());
    }

}
