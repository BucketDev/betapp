package com.bucketdev.betapp.endpoint.leaderboard;

import com.bucketdev.betapp.dto.leaderboard.LeaderboardDTO;
import com.bucketdev.betapp.service.leaderboard.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService service;

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<LeaderboardDTO>> findByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findByTournamentId(tournamentId), HttpStatus.OK);
    }


}
