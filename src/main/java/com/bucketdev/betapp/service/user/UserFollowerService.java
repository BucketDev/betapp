package com.bucketdev.betapp.service.user;

import com.bucketdev.betapp.dto.user.SubUserFollowerDTO;
import com.bucketdev.betapp.dto.user.UserDetailsDTO;
import com.bucketdev.betapp.dto.user.UserFollowerCountDTO;
import com.bucketdev.betapp.dto.user.UserFollowerDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface UserFollowerService {

    UserFollowerDTO findByUid(String uid);
    UserFollowerCountDTO findCountByUid(String uid);
    List<UserDetailsDTO> findDetailsByDisplayName(String displayName, String followingUid);
    UserDetailsDTO findDetailsByUid(String uid, String followingUid);
    SubUserFollowerDTO follow(String fromUid, String toUid);
    void unfollow(String fromUid, String toUid);

}
