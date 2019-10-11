package com.bucketdev.betapp.repository.leaderboard;

import com.bucketdev.betapp.domain.leaderboard.Leaderboard;
import com.bucketdev.betapp.domain.leaderboard.LeaderboardKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, LeaderboardKey> {

    List<Leaderboard> findByLeaderboardKeyTournamentId(long tournamentId);

}
