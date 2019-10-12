package com.bucketdev.betapp.dto.group;

import com.bucketdev.betapp.dto.match.TeamDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class GroupTeamDTO implements Serializable {

    private long id;
    private long groupId;
    private char groupName;
    private long tournamentId;
    private TeamDTO team;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesTied;
    private int gamesLost;
    private int points;

}
