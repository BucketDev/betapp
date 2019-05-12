package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.UserDTO;

/**
 * @author rodrigo.loyola
 */
public interface UserService {

    UserDTO save(UserDTO dto);
    UserDTO findByUid(String uid);

}
