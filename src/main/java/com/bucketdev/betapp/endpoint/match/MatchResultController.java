package com.bucketdev.betapp.endpoint.match;

import com.bucketdev.betapp.dto.match.MatchResultDTO;
import com.bucketdev.betapp.dto.match.ParticipantResultsDTO;
import com.bucketdev.betapp.service.match.MatchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/matchResults")
public class MatchResultController {

    @Autowired
    private MatchResultService service;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<MatchResultDTO> upsert(@RequestBody MatchResultDTO dto) {
        return new ResponseEntity<>(service.upsert(dto), HttpStatus.CREATED);
    }

    @GetMapping("/matchTeam/{matchTeamId}")
    public ResponseEntity<Set<ParticipantResultsDTO>> findByMatchTeamId(@PathVariable long matchTeamId) {
        return new ResponseEntity<>(service.findByMatchTeamId(matchTeamId), HttpStatus.OK);
    }

}
