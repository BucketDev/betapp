package com.bucketdev.betapp.endpoint.group;

import com.bucketdev.betapp.dto.group.GroupParticipantDTO;
import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.service.group.GroupParticipantService;
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
    public ResponseEntity<GroupParticipantDTO> insert(@RequestBody GroupParticipantDTO dto) {
        return new ResponseEntity<>(service.insert(dto), HttpStatus.CREATED);
    }

    @PostMapping("/group/{groupId}")
    public ResponseEntity<List<GroupParticipantDTO>> saveByGroupId(@PathVariable long groupId,
                                                                    @RequestBody List<UserDTO> users) {
        return new ResponseEntity<>(service.saveByGroupId(groupId, users), HttpStatus.CREATED);
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<GroupParticipantDTO>> findByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findByTournamentId(tournamentId), HttpStatus.OK);
    }

}
