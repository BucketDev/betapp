package com.bucketdev.betapp.service.user;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.dto.user.UserFollowerCountDTO;
import com.bucketdev.betapp.dto.user.UserFollowerDTO;

/**
 * @author rodrigo.loyola
 */
public interface UserFollowerService {

    UserFollowerDTO findByUid(String uid);
    UserFollowerCountDTO findCountByUid(String uid);
    UserDTO follow(String fromUid, String toUid);
    void unfollow(String fromUid, String toUid);

}
