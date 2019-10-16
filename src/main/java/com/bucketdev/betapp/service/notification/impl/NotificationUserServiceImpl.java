package com.bucketdev.betapp.service.notification.impl;

import com.bucketdev.betapp.domain.notification.NotificationUser;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.notification.NotificationUserDTO;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.notification.NotificationUserRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.notification.NotificationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class NotificationUserServiceImpl implements NotificationUserService {

    @Autowired
    private NotificationUserRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<NotificationUserDTO> findByFollowingUserUid(String uid) {
        User user = userRepository.findByUid(uid);
        if (user == null)
            throw new UserNotFoundException("uid", uid);
        Set<NotificationUser> notificationUsers = repository.findAllByUserFollowingId(user.getId());
        return notificationUsers.stream().map(NotificationUser::toDTO).collect(Collectors.toSet());
    }
}
