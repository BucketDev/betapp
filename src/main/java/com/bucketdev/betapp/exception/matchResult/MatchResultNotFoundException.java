package com.bucketdev.betapp.exception.matchResult;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchResultNotFoundException extends RuntimeException {

    public MatchResultNotFoundException(String message) {
        super(message);
    }
}
