package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.service.MatchParticipantsService;
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
@RequestMapping("/matchParticipants")
public class MatchParticipantsController {

    @Autowired
    private MatchParticipantsService service;

    @GetMapping("tournament/{tournamentId}")
    public ResponseEntity<List<MatchParticipantsDTO>> findAllByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findAllByTournamentId(tournamentId), HttpStatus.OK);
    }

}
