package com.bucketdev.betapp.exception.tournament;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(String... message) {
        super(Arrays.stream(message).reduce((s, s2) -> s + " " + s2).orElse(""));
    }

}
