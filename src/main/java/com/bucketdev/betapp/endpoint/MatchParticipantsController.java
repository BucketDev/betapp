package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.service.MatchParticipantsService;
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
@RequestMapping("/matchParticipants")
public class MatchParticipantsController {

    @Autowired
    private MatchParticipantsService service;

    @GetMapping("tournament/{tournamentId}")
    public ResponseEntity<Map<Integer, List<MatchParticipantsDTO>>> findAllByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findAllByTournamentId(tournamentId), HttpStatus.OK);
    }

    @GetMapping("tournament/{tournamentId}/playoffs")
    public ResponseEntity<Map<PlayoffStage, List<MatchParticipantsDTO>>> findAllPlayoffsByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findAllPlayoffsByTournamentId(tournamentId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MatchParticipantsDTO> update(@RequestBody MatchParticipantsDTO dto) {
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

}
