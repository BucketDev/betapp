package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.Group;
import com.bucketdev.betapp.dto.GroupDTO;
import com.bucketdev.betapp.exception.group.GroupNotFoundException;
import com.bucketdev.betapp.repository.GroupRepository;
import com.bucketdev.betapp.service.GroupService;
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
    public List<GroupDTO> findByTournamentUid(String uid) {
        return repository.findAllByTournamentUidOrderByName(uid).stream().map(Group::toDTO).collect(Collectors.toList());
    }

}
