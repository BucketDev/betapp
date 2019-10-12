package com.bucketdev.betapp.exception.tournament;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TournamentSettingsNotFoundException extends RuntimeException {

    public TournamentSettingsNotFoundException(String message) {
        super(message);
    }
}
