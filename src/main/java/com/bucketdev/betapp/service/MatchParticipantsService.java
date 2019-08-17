package com.bucketdev.betapp.service;

import com.bucketdev.betapp.domain.Group;
import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.type.PlayoffStage;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface MatchParticipantsService {

    List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId);

    List<MatchParticipantsDTO> findAllPlayoffsByTournamentId(long tournamentId);

    MatchParticipantsDTO update(MatchParticipantsDTO dto);

    void calculateMatches(Group group, boolean roundTrip, PlayoffStage playoffStage);
}
