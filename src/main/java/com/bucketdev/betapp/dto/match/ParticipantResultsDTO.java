package com.bucketdev.betapp.dto.match;

import com.bucketdev.betapp.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class ParticipantResultsDTO implements Serializable {

    private long id;
    private UserDTO user;
    private MatchResultDTO matchResult;

}
