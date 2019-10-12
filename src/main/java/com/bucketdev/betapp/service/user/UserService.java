package com.bucketdev.betapp.service.user;

import com.bucketdev.betapp.dto.user.UserDTO;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
public interface UserService {

    UserDTO save(UserDTO dto);
    UserDTO findByUid(String uid);
    Set<UserDTO> findByDisplayName(String uid);

}
