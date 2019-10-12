package com.bucketdev.betapp.service.match;

import com.bucketdev.betapp.domain.match.MatchTeams;
import com.bucketdev.betapp.dto.match.MatchResultDTO;
import com.bucketdev.betapp.dto.match.ParticipantResultsDTO;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
public interface MatchResultService {

    MatchResultDTO upsert(MatchResultDTO dto);
    Set<ParticipantResultsDTO> findByMatchTeamId(long matchTeamId);
    void calculateBetPoints(MatchTeams matchTeams);
}
