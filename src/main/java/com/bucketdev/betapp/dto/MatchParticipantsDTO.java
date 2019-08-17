package com.bucketdev.betapp.dto;

import com.bucketdev.betapp.type.PlayoffStage;
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
public class MatchParticipantsDTO implements Serializable {

    private long id;
    private long tournamentId;
    private GroupParticipantDTO groupParticipantAway;
    private Integer scoreAway;
    private GroupParticipantDTO groupParticipantHome;
    private Integer scoreHome;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar scheduledTime;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar registeredTime;
    private PlayoffStage playoffStage;
    private int round;

}
