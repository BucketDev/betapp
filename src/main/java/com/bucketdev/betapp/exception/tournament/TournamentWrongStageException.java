package com.bucketdev.betapp.exception.tournament;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TournamentWrongStageException extends RuntimeException {

    public TournamentWrongStageException(String message) {
        super(message);
    }
}
