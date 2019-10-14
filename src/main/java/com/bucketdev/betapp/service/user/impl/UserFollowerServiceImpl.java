package com.bucketdev.betapp.service.user.impl;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.domain.user.UserFollower;
import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.dto.user.UserFollowerCountDTO;
import com.bucketdev.betapp.dto.user.UserFollowerDTO;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.user.UserFollowerRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.user.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rodrigo.loyola
 */
@Service
public class UserFollowerServiceImpl implements UserFollowerService {

    @Autowired
    private UserFollowerRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserFollowerDTO findByUid(String uid) {
        UserFollower userFollower = repository.findByUid(uid);
        if (userFollower == null)
            throw new UserNotFoundException("uid: " + uid);
        return userFollower.toDTO();
    }

    @Override
    public UserFollowerCountDTO findCountByUid(String uid) {
        UserFollower userFollower = repository.findByUid(uid);
        if (userFollower == null)
            throw new UserNotFoundException("uid: " + uid);
        return userFollower.toCountDTO();
    }

    @Override
    public UserDTO follow(String fromUid, String toUid) {
        User user = userRepository.findByUid(toUid);
        if (user == null)
            throw new UserNotFoundException("uid: " + toUid);
        UserFollower userFollower = repository.findByUid(fromUid);
        if (userFollower == null)
            throw new UserNotFoundException("uid:", fromUid);
        userFollower.getFollowing().add(user);

        repository.save(userFollower);

        return user.toDTO();
    }

    @Override
    public void unfollow(String fromUid, String toUid) {
        UserFollower userFollower = repository.findByUid(fromUid);
        if (userFollower == null)
            throw new UserNotFoundException("uid:", fromUid);
        userFollower.getFollowing().removeIf(user -> user.getUid().equals(toUid));

        repository.save(userFollower);
    }
}
