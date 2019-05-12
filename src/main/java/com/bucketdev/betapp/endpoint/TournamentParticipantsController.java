package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.TournamentParticipantsDTO;
import com.bucketdev.betapp.service.TournamentParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/tournamentParticipants")
public class TournamentParticipantsController {

    @Autowired
    private TournamentParticipantsService service;

    @GetMapping("/participant/{uid}")
    public ResponseEntity<Set<TournamentParticipantsDTO>> findByParticipantUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByParticipantUid(uid), HttpStatus.OK);
    }

}
