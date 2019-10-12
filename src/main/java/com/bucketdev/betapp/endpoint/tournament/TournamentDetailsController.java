package com.bucketdev.betapp.endpoint.tournament;

import com.bucketdev.betapp.dto.tournament.TournamentDetailsDTO;
import com.bucketdev.betapp.service.tournament.TournamentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/tournamentDetails")
public class TournamentDetailsController {

    @Autowired
    private TournamentDetailsService service;

    @GetMapping("/{uid}")
    public ResponseEntity<TournamentDetailsDTO> findByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByUid(uid), HttpStatus.OK);
    }

}
