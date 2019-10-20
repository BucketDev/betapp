package com.bucketdev.betapp.service.notification.impl;

import com.bucketdev.betapp.domain.NotificationDestiny;
import com.bucketdev.betapp.domain.notification.Notification;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.repository.notification.NotificationRepository;
import com.bucketdev.betapp.service.notification.NotificationService;
import com.bucketdev.betapp.type.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author rodrigo.loyola
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Override
    @Async
    public void create(NotificationType notificationType, User userCreation, NotificationDestiny destiny) {
        Notification notification = new Notification(notificationType, userCreation, destiny);
        repository.save(notification);
    }

}
