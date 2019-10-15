package com.bucketdev.betapp.service.user.impl;

import com.bucketdev.betapp.domain.user.UserNotification;
import com.bucketdev.betapp.dto.user.UserNotificationDTO;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.UserNotificationRepository;
import com.bucketdev.betapp.service.user.UserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rodrigo.loyola
 */
@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepository repository;

    @Override
    public UserNotificationDTO findByUid(String uid) {
        UserNotification userNotification = repository.findByUid(uid);
        if (userNotification == null)
            throw new UserNotFoundException("uid:", uid);
        return userNotification.toDTO();
    }
}
