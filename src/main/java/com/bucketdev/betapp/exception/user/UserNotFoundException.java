package com.bucketdev.betapp.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Optional;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String... message) {
        super(Arrays.stream(message).reduce((s, s2) -> s + " " + s2).orElse(""));
    }
}
