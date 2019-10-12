package com.bucketdev.betapp.service.user.impl;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDTO save(UserDTO dto) {
        User user = new User();
        if(dto.getId() > 0) {
            Optional<User> userOptional = repository.findById(dto.getId());
            if(!userOptional.isPresent())
                throw new UserNotFoundException("id: " + dto.getId());
            user = userOptional.get();
        }
        user.setValuesFromDTO(dto);
        return repository.save(user).toDTO();
    }

    @Override
    public UserDTO findByUid(String uid) {
        return repository.findByUid(uid).toDTO();
    }

    @Override
    public Set<UserDTO> findByDisplayName(String name) {
        return repository.findByDisplayNameContaining(name).stream().map(User::toDTO).collect(Collectors.toSet());
    }

}
