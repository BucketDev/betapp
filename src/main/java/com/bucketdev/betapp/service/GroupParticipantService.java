package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.GroupParticipantDTO;
import com.bucketdev.betapp.dto.UserDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface GroupParticipantService {

    UserDTO save(GroupParticipantDTO groupParticipantDTO);

    List<GroupParticipantDTO> findByTournamentId(long tournamentId);

}
