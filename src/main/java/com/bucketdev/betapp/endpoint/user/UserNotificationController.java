package com.bucketdev.betapp.endpoint.user;

import com.bucketdev.betapp.dto.user.UserNotificationDTO;
import com.bucketdev.betapp.service.user.UserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/userNotifications")
public class UserNotificationController {

    @Autowired
    private UserNotificationService service;

    @GetMapping("/uid/{uid}")
    public ResponseEntity<UserNotificationDTO> findByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByUid(uid), HttpStatus.OK);
    }

}
