package com.bucketdev.betapp.exception.group;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class GroupTeamsNotSufficient extends RuntimeException {

    public GroupTeamsNotSufficient(String message) {
        super(message);
    }
}

