package com.bucketdev.betapp.endpoint.match;

import com.bucketdev.betapp.dto.match.ParticipantDTO;
import com.bucketdev.betapp.service.match.ParticipantService;
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
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService service;

    @GetMapping("/pendingGroupByTournament/{tournamentId}")
    public ResponseEntity<List<ParticipantDTO>> pendingGroupByTournament(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.pendingGroupByTournament(tournamentId), HttpStatus.OK);
    }

}
