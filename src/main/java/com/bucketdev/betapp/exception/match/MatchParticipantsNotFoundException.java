package com.bucketdev.betapp.exception.match;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchParticipantsNotFoundException extends RuntimeException {

    public MatchParticipantsNotFoundException(String message) {
        super(message);
    }
}
