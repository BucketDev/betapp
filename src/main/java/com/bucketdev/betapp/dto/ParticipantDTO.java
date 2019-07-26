package com.bucketdev.betapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class ParticipantDTO {

    private long id;
    private long tournamentId;
    private UserDTO user;

}
