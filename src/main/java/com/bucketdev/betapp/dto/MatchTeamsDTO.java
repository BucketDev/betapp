package com.bucketdev.betapp.dto;

import com.bucketdev.betapp.type.PlayoffStage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class MatchTeamsDTO implements Serializable {

    private long id;
    private long tournamentId;
    private char groupName;
    private GroupTeamDTO groupTeamAway;
    private Integer scoreAway;
    private GroupTeamDTO groupTeamHome;
    private Integer  scoreHome;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar scheduledTime;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar registeredTime;
    private PlayoffStage playoffStage;
    private int round;
    private MatchResultDTO matchResult;

}
