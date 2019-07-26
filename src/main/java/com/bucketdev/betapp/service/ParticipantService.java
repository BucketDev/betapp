package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.ParticipantDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface ParticipantService {

    List<ParticipantDTO> pendingGroupByTournament(long tournamentId);

}
