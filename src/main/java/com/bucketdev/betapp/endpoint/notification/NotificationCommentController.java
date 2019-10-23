package com.bucketdev.betapp.endpoint.notification;

import com.bucketdev.betapp.dto.notification.NotificationCommentDTO;
import com.bucketdev.betapp.service.notification.NotificationCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/notificationComments")
public class NotificationCommentController {

    @Autowired
    private NotificationCommentService service;

    @GetMapping("/notification/{notificationId}")
    public ResponseEntity<List<NotificationCommentDTO>> findAllByNotificationId(@PathVariable Long notificationId) {
        return new ResponseEntity<>(service.findAllByNotificationId(notificationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotificationCommentDTO> save(@RequestBody NotificationCommentDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }
}
