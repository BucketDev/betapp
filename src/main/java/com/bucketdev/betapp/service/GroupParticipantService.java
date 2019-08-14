package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.GroupParticipantDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface GroupParticipantService {

    GroupParticipantDTO save(GroupParticipantDTO groupParticipantDTO);

    List<GroupParticipantDTO> findByTournamentId(long tournamentId);

}
