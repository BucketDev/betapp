package com.bucketdev.betapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class GroupParticipantDTO implements Serializable {

    private long id;
    private GroupDTO group;
    private UserDTO user;

}
