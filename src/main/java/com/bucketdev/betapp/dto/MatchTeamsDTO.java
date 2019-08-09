package com.bucketdev.betapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class MatchTeamsDTO implements Serializable {

    private long id;
    private long tournamentId;
    private GroupTeamDTO groupTeamAway;
    private int scoreAway;
    private GroupTeamDTO groupTeamHome;
    private int scoreHome;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar scheduledTime;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar registeredTime;

}
