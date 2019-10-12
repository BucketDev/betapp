package com.bucketdev.betapp.service.match;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.dto.match.MatchTeamsDTO;
import com.bucketdev.betapp.type.PlayoffStage;

import java.util.List;
import java.util.Map;

public interface MatchTeamsService {

    Map<Integer, List<MatchTeamsDTO>> findAllByTournamentId(long tournamentId, String userUid);

    Map<PlayoffStage, List<MatchTeamsDTO>> findAllPlayoffsByTournamentId(long tournamentId, String userUid);

    MatchTeamsDTO update(MatchTeamsDTO dto);

    void calculateMatches(Group group, boolean roundTrip, PlayoffStage playoffStage);
}
