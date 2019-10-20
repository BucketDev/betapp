package com.bucketdev.betapp.service.notification;

import com.bucketdev.betapp.domain.NotificationDestiny;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.type.NotificationType;

/**
 * @author rodrigo.loyola
 */
public interface NotificationService {

    void create(NotificationType notificationType, User userCreation, NotificationDestiny destiny);

}
