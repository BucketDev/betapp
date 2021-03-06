package com.bucketdev.betapp.dto.group;

import com.bucketdev.betapp.dto.user.UserDTO;
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
    private long groupId;
    private char groupName;
    private long tournamentId;
    private UserDTO user;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesTied;
    private int gamesLost;
    private int points;

}
