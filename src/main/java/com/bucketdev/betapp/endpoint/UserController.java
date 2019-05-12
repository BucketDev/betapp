package com.bucketdev.betapp.endpoint;

import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{uid}")
    public ResponseEntity<UserDTO> findByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByUid(uid), HttpStatus.OK);
    }

}
