package com.bucketdev.betapp.exception.matchTeams;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchTeamsNotFoundException extends RuntimeException {

    public MatchTeamsNotFoundException(String message) {
        super(message);
    }
}
