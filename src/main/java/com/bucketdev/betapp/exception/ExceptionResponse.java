package com.bucketdev.betapp.exception;

import lombok.Getter;

import java.util.Date;

/**
 * @author rodrigo.loyola
 */
@Getter
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String detail;

    public ExceptionResponse(Date timestamp, String message, String detail) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }

}
