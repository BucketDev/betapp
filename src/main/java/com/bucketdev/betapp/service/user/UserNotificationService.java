package com.bucketdev.betapp.service.user;

import com.bucketdev.betapp.dto.user.UserNotificationDTO;

/**
 * @author rodrigo.loyola
 */
public interface UserNotificationService {

    UserNotificationDTO findByUid(String uid);

}
