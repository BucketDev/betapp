package com.bucketdev.betapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class GroupDTO implements Serializable {

    private long id;
    private char name;
    private long tournamentId;
    private List<GroupParticipantDTO> groupParticipants;

}
