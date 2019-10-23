package com.bucketdev.betapp.endpoint.user;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.service.mail.MailService;
import com.bucketdev.betapp.service.user.UserService;
import com.bucketdev.betapp.type.MailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<UserDTO> upsert(@RequestBody UserDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<UserDTO> findByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByUid(uid), HttpStatus.OK);
    }

    @GetMapping("/displayName/{name}")
    public ResponseEntity<Set<UserDTO>> findByDisplayName(@PathVariable String name) {
        return new ResponseEntity<>(service.findByDisplayName(name), HttpStatus.OK);
    }

}
