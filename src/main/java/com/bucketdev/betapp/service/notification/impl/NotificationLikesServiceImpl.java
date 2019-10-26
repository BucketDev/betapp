package com.bucketdev.betapp.service.notification.impl;

import com.bucketdev.betapp.domain.notification.Notification;
import com.bucketdev.betapp.domain.notification.NotificationLikes;
import com.bucketdev.betapp.domain.notification.NotificationLikesKey;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.exception.notification.NotificationLikesNotFoundException;
import com.bucketdev.betapp.exception.notification.NotificationNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.notification.NotificationLikesRepository;
import com.bucketdev.betapp.repository.notification.NotificationRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.notification.NotificationLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author rodrigo.loyola
 */
@Service
public class NotificationLikesServiceImpl implements NotificationLikesService {

    @Autowired
    private NotificationLikesRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public boolean create(long notificationId, long userId) {
        NotificationLikesKey key = new NotificationLikesKey(notificationId, userId);
        NotificationLikes notificationLikes = new NotificationLikes(key);

        try {
            repository.save(notificationLikes);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void delete(long notificationId, long userId) {
        NotificationLikes notificationLikes = repository.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationLikes == null)
            throw new NotificationLikesNotFoundException("userId:", String.valueOf(userId), "notificationId:", String.valueOf(notificationId));
        repository.delete(notificationLikes);
    }
}
