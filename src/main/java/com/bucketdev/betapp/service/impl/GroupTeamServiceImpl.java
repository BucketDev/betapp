package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.Group;
import com.bucketdev.betapp.domain.GroupTeam;
import com.bucketdev.betapp.domain.Team;
import com.bucketdev.betapp.dto.GroupTeamDTO;
import com.bucketdev.betapp.dto.TeamDTO;
import com.bucketdev.betapp.exception.group.GroupNotFoundException;
import com.bucketdev.betapp.repository.GroupRepository;
import com.bucketdev.betapp.repository.GroupTeamRepository;
import com.bucketdev.betapp.repository.TeamRepository;
import com.bucketdev.betapp.service.GroupTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupTeamServiceImpl implements GroupTeamService {

    @Autowired
    private GroupTeamRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public GroupTeamDTO insert(GroupTeamDTO groupTeamDTO) {
        Optional<Group> optionalGroup = groupRepository.findById(groupTeamDTO.getGroupId());
        if (!optionalGroup.isPresent())
            throw new GroupNotFoundException("id: " + groupTeamDTO.getGroupId());
        Group group = optionalGroup.get();

        Team team = new Team();
        TeamDTO teamDTO = groupTeamDTO.getTeam();
        team.setValuesFromDTO(teamDTO);
        team.setTournament(group.getTournament());
        team = teamRepository.save(team);

        GroupTeam groupTeam = new GroupTeam( group, team, 0);

        return repository.save(groupTeam).toDTO();
    }
}
