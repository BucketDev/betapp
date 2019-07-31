package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.GroupParticipantDTO;
import com.bucketdev.betapp.service.GroupParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/groupParticipants")
public class GroupParticipantController {

    @Autowired
    private GroupParticipantService service;

    @PostMapping
    public ResponseEntity<GroupParticipantDTO> saveGroupParticipant(@RequestBody GroupParticipantDTO groupParticipant) {
        return new ResponseEntity<>(service.save(groupParticipant), HttpStatus.CREATED);
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<GroupParticipantDTO>> findByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findByTournamentId(tournamentId), HttpStatus.OK);
    }

}
