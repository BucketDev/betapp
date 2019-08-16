package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    GroupDTO save(GroupDTO dto);

    List<GroupDTO> findAllByTournamentUid(String uid);

    List<GroupDTO> findAllPlayoffsByTournamentUid(String uid);
}
