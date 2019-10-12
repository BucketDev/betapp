package com.bucketdev.betapp.dto.match;

import com.bucketdev.betapp.dto.user.UserDTO;
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
