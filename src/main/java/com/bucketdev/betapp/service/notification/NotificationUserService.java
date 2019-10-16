package com.bucketdev.betapp.service.notification;

import com.bucketdev.betapp.dto.notification.NotificationUserDTO;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
public interface NotificationUserService {

    Set<NotificationUserDTO> findByFollowingUserUid(String uid);

}
