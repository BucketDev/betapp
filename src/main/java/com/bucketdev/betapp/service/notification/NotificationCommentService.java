package com.bucketdev.betapp.service.notification;

import com.bucketdev.betapp.dto.notification.NotificationCommentDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface NotificationCommentService {

    List<NotificationCommentDTO> findAllByNotificationId(long commentId);
    NotificationCommentDTO save(NotificationCommentDTO dto);

}
