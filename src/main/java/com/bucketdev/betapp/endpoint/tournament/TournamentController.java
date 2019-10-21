package com.bucketdev.betapp.endpoint.tournament;

import com.bucketdev.betapp.dto.tournament.TournamentDTO;
import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.service.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService service;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<TournamentDTO> upsert(@RequestBody TournamentDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{tournamentId}/participant")
    public ResponseEntity<UserDTO> addParticipant(@PathVariable long tournamentId, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(service.addParticipant(tournamentId, userDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{tournamentId}/participants")
    public ResponseEntity deleteParticipants(@PathVariable long tournamentId, @RequestBody List<UserDTO> usersDTO) {
        service.deleteParticipants(tournamentId, usersDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<TournamentDTO> updatePhotoUrl(@PathVariable long id, @RequestBody TournamentDTO dto) {
        return new ResponseEntity<>(service.updatePhotoUrl(id, dto), HttpStatus.OK);
    }

    @PutMapping("/stage")
    public ResponseEntity<TournamentDTO> updateTournamentStage(@RequestBody TournamentDTO dto) {
        return new ResponseEntity<>(service.updateTournamentStage(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity deleteTournament(@PathVariable String uid) {
        service.deleteTournament(uid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
