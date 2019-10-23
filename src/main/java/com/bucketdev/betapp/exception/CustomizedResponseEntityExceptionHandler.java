package com.bucketdev.betapp.exception;

import com.bucketdev.betapp.exception.group.GroupParticipantsNotSufficient;
import com.bucketdev.betapp.exception.group.GroupTeamsNotSufficient;
import com.bucketdev.betapp.exception.match.MatchParticipantsNotFoundException;
import com.bucketdev.betapp.exception.match.MatchResultNotFoundException;
import com.bucketdev.betapp.exception.match.MatchTeamsNotFoundException;
import com.bucketdev.betapp.exception.notification.NotificationNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentSettingsNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(true));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TournamentSettingsNotFoundException.class)
    public final ResponseEntity<Object> handleTournamentSettingsNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatchParticipantsNotFoundException.class)
    public final ResponseEntity<Object> handleMatchParticipantsNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatchResultNotFoundException.class)
    public final ResponseEntity<Object> handleMatchResultNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatchTeamsNotFoundException.class)
    public final ResponseEntity<Object> handleMatchTeamsNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupParticipantsNotSufficient.class)
    public final ResponseEntity<Object> handleGroupParticipantsNotSufficient(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupTeamsNotSufficient.class)
    public final ResponseEntity<Object> handleGroupTeamsNotSufficient(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MailException.class)
    public final ResponseEntity<Object> handleMailException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public final ResponseEntity<Object> handleNotificationNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}