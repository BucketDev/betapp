package com.bucketdev.betapp.service.group.impl;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.domain.group.GroupTeam;
import com.bucketdev.betapp.domain.match.Team;
import com.bucketdev.betapp.dto.group.GroupTeamDTO;
import com.bucketdev.betapp.dto.match.TeamDTO;
import com.bucketdev.betapp.exception.group.GroupNotFoundException;
import com.bucketdev.betapp.repository.group.GroupRepository;
import com.bucketdev.betapp.repository.group.GroupTeamRepository;
import com.bucketdev.betapp.repository.match.TeamRepository;
import com.bucketdev.betapp.service.group.GroupTeamService;
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

        GroupTeam groupTeam = new GroupTeam( group, team, groupTeamDTO.getPoints());

        return repository.save(groupTeam).toDTO();
    }
}
