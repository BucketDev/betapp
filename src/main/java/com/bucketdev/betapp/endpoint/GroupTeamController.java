package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.GroupTeamDTO;
import com.bucketdev.betapp.service.GroupTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groupTeams")
public class GroupTeamController {

    @Autowired
    private GroupTeamService service;

    @PostMapping
    public ResponseEntity<GroupTeamDTO> insert(@RequestBody GroupTeamDTO dto) {
        return new ResponseEntity<>(service.insert(dto), HttpStatus.CREATED);
    }

}
