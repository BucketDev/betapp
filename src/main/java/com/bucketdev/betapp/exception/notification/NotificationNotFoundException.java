package com.bucketdev.betapp.exception.notification;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(String... message) {
        super(Arrays.stream(message).reduce((s, s2) -> s + " " + s2).orElse(""));
    }
}
