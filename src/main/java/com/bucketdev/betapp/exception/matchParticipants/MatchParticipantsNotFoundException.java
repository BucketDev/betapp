package com.bucketdev.betapp.exception.matchParticipants;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchParticipantsNotFoundException extends RuntimeException {

    public MatchParticipantsNotFoundException(String message) {
        super(message);
    }
}
