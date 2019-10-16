package com.bucketdev.betapp.endpoint.notification;

import com.bucketdev.betapp.dto.notification.NotificationUserDTO;
import com.bucketdev.betapp.service.notification.NotificationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/notificationUsers")
public class NotificationUserController {

    @Autowired
    private NotificationUserService service;

    @GetMapping("/uid/{uid}")
    public ResponseEntity<Set<NotificationUserDTO>> findByUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findByFollowingUserUid(uid), HttpStatus.OK);
    }

}
