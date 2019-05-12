package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.User;
import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.UserRepository;
import com.bucketdev.betapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author rodrigo.loyola
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDTO save(UserDTO dto) {
        User user = new User();
        if(dto.getUserId() > 0) {
            Optional<User> userOptional = repository.findById(dto.getUserId());
            if(!userOptional.isPresent())
                throw new UserNotFoundException("id: " + dto.getUserId());
            user = userOptional.get();
        }
        user.setValuesFromDTO(dto);
        return repository.save(user).toDTO();
    }

    @Override
    public UserDTO findByUid(String uid) {
        return repository.findByUid(uid).toDTO();
    }

}
