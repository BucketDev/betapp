package com.bucketdev.betapp.endpoint.notification;

import com.bucketdev.betapp.domain.notification.NotificationLikes;
import com.bucketdev.betapp.repository.notification.NotificationLikesRepository;
import com.bucketdev.betapp.service.notification.NotificationLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/notificationLikes")
public class NotificationLikesController {

    @Autowired
    private NotificationLikesService service;

    @PostMapping("notification/{notificationId}/user/{userId}")
    public ResponseEntity<Boolean> create(@PathVariable long notificationId, @PathVariable long userId) {
        return new ResponseEntity<>(service.create(notificationId, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("notification/{notificationId}/user/{userId}")
    public ResponseEntity delete(@PathVariable long notificationId, @PathVariable long userId) {
        service.delete(notificationId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
