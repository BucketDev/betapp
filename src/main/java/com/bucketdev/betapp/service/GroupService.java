package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    GroupDTO save(GroupDTO dto);

    List<GroupDTO> findByTournamentUid(String uid);

}
