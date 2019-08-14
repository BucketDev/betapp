package com.bucketdev.betapp.exception.groupParticipant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class GroupParticipantsNotSufficient extends RuntimeException {

    public GroupParticipantsNotSufficient(String message) {
        super(message);
    }
}
