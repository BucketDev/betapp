package com.bucketdev.betapp.endpoint.user;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.dto.user.UserDetailsDTO;
import com.bucketdev.betapp.dto.user.UserFollowerCountDTO;
import com.bucketdev.betapp.dto.user.UserFollowerDTO;
import com.bucketdev.betapp.service.user.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/userFollowers")
public class UserFollowerController {

    @Autowired
    private UserFollowerService service;

    @GetMapping("/uid/{uid}")
    public ResponseEntity<UserFollowerDTO> findByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByUid(uid), HttpStatus.OK);
    }

    @GetMapping("count/uid/{uid}")
    public ResponseEntity<UserFollowerCountDTO> findCountByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findCountByUid(uid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDetailsDTO> findFollowingByUid(@RequestParam(value = "uid") String uid,
                                                             @RequestParam(value = "followingUid") String followingUid) {
        return new ResponseEntity<>(service.findDetailsByUid(uid, followingUid), HttpStatus.OK);
    }

    @PostMapping("from/{fromUid}/to/{toUid}")
    public ResponseEntity<UserDTO> follow(@PathVariable String fromUid, @PathVariable String toUid) {
        return new ResponseEntity<>(service.follow(fromUid, toUid), HttpStatus.CREATED);
    }

    @DeleteMapping("from/{fromUid}/to/{toUid}")
    public ResponseEntity unfollow(@PathVariable String fromUid, @PathVariable String toUid) {
        service.unfollow(fromUid, toUid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
