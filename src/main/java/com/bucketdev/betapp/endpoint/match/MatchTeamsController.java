package com.bucketdev.betapp.endpoint.match;

import com.bucketdev.betapp.dto.match.MatchTeamsDTO;
import com.bucketdev.betapp.service.match.MatchTeamsService;
import com.bucketdev.betapp.type.PlayoffStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/matchTeams")
public class MatchTeamsController {

    @Autowired
    private MatchTeamsService service;

    @GetMapping("tournament/{tournamentId}")
    public ResponseEntity<Map<Integer, List<MatchTeamsDTO>>> findAllByTournamentId(
            @PathVariable long tournamentId,
            @RequestParam(value = "userUid") String userUid) {
        return new ResponseEntity<>(service.findAllByTournamentId(tournamentId, userUid), HttpStatus.OK);
    }

    @GetMapping("tournament/{tournamentId}/playoffs")
    public ResponseEntity<Map<PlayoffStage, List<MatchTeamsDTO>>> findAllPlayoffsByTournamentId(
            @PathVariable long tournamentId,
            @RequestParam(value = "userUid") String userUid) {
        return new ResponseEntity<>(service.findAllPlayoffsByTournamentId(tournamentId, userUid), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MatchTeamsDTO> update(@RequestBody MatchTeamsDTO dto) {
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }
}
