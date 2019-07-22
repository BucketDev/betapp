package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.GroupDTO;

import java.util.Set;

public interface GroupService {

    GroupDTO save(GroupDTO dto);

    Set<GroupDTO> findByTournamentUid(String uid);

}
