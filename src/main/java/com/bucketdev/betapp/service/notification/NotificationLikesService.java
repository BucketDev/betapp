package com.bucketdev.betapp.service.notification;

/**
 * @author rodrigo.loyola
 */
public interface NotificationLikesService {

    boolean create(long notificationId, long userId);
    void delete(long notificationId, long userId);

}
