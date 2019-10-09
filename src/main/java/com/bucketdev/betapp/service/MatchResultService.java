package com.bucketdev.betapp.service;

import com.bucketdev.betapp.domain.MatchTeams;
import com.bucketdev.betapp.dto.MatchResultDTO;
import com.bucketdev.betapp.dto.ParticipantResultsDTO;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
public interface MatchResultService {

    MatchResultDTO upsert(MatchResultDTO dto);
    Set<ParticipantResultsDTO> findByMatchTeamId(long matchTeamId);
    void calculateBetPoints(MatchTeams matchTeams);
}
