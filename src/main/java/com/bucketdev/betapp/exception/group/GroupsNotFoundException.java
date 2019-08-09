package com.bucketdev.betapp.exception.group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rodrigo.loyola
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroupsNotFoundException extends RuntimeException {

    public GroupsNotFoundException(String message) {
        super(message);
    }
}
