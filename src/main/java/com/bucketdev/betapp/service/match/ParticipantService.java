package com.bucketdev.betapp.service.match;

import com.bucketdev.betapp.dto.match.ParticipantDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface ParticipantService {

    List<ParticipantDTO> pendingGroupByTournament(long tournamentId);

}
