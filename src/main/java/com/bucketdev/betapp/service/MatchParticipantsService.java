package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.MatchParticipantsDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface MatchParticipantsService {

    List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId);

    List<MatchParticipantsDTO> findAllPlayoffsByTournamentId(long tournamentId);

    MatchParticipantsDTO update(MatchParticipantsDTO dto);
}
