package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.GroupParticipantDTO;
import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.service.GroupParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/groupParticipants")
public class GroupParticipantController {

    @Autowired
    private GroupParticipantService service;

    @PostMapping()
    public ResponseEntity<UserDTO> saveGroupParticipant(@RequestBody GroupParticipantDTO groupParticipant) {
        return new ResponseEntity<>(service.save(groupParticipant), HttpStatus.CREATED);
    }

}
