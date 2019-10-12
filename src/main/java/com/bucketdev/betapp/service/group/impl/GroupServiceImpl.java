package com.bucketdev.betapp.service.group.impl;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.dto.group.GroupDTO;
import com.bucketdev.betapp.exception.group.GroupNotFoundException;
import com.bucketdev.betapp.repository.group.GroupRepository;
import com.bucketdev.betapp.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;

    public GroupDTO save(GroupDTO dto) {
        Group group = new Group(dto.getName());
        if (dto.getId() > 0) {
            Optional<Group> groupOptional = repository.findById(dto.getId());
            if (!groupOptional.isPresent())
                throw new GroupNotFoundException("id: " + dto.getId());
            group = groupOptional.get();
        }
        return repository.save(group).toDTO();
    }

    @Override
    public List<GroupDTO> findAllByTournamentUid(String uid) {
        return repository.findAllByTournamentUid(uid).stream().map(Group::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GroupDTO> findAllPlayoffsByTournamentUid(String uid) {
        return repository.findAllPlayoffsByTournamentUid(uid).stream().map(Group::toDTO).collect(Collectors.toList());
    }

}
