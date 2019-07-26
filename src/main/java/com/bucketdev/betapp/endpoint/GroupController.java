package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.GroupDTO;
import com.bucketdev.betapp.service.GroupService;
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
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @GetMapping("/tournament/{uid}")
    public ResponseEntity<List<GroupDTO>> findByTournamentUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByTournamentUid(uid), HttpStatus.OK);
    }

}
