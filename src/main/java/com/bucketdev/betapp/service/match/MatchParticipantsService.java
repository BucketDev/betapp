package com.bucketdev.betapp.service.match;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.dto.match.MatchParticipantsDTO;
import com.bucketdev.betapp.type.PlayoffStage;

import java.util.List;
import java.util.Map;

/**
 * @author rodrigo.loyola
 */
public interface MatchParticipantsService {

    Map<Integer, List<MatchParticipantsDTO>> findAllByTournamentId(long tournamentId);

    Map<PlayoffStage, List<MatchParticipantsDTO>> findAllPlayoffsByTournamentId(long tournamentId);

    MatchParticipantsDTO update(MatchParticipantsDTO dto);

    void calculateMatches(Group group, boolean roundTrip, PlayoffStage playoffStage);
}
