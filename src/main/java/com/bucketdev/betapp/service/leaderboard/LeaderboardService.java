package com.bucketdev.betapp.service.leaderboard;

import com.bucketdev.betapp.dto.leaderboard.LeaderboardDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface LeaderboardService {

    List<LeaderboardDTO> findByTournamentId(long tournamentId);

}
