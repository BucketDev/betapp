package com.bucketdev.betapp.service.leaderboard.impl;

import com.bucketdev.betapp.domain.leaderboard.Leaderboard;
import com.bucketdev.betapp.dto.leaderboard.LeaderboardDTO;
import com.bucketdev.betapp.repository.leaderboard.LeaderboardRepository;
import com.bucketdev.betapp.service.leaderboard.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private LeaderboardRepository repository;

    @Override
    public List<LeaderboardDTO> findByTournamentId(long tournamentId) {
        return repository.findByLeaderboardKeyTournamentId(tournamentId).stream()
                .map(Leaderboard::toDTO).collect(Collectors.toList());
    }
}
