package com.bucketdev.betapp.dto;

import com.bucketdev.betapp.type.TournamentPrivacy;
import com.bucketdev.betapp.type.TournamentStage;
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
public class TournamentDTO implements Serializable {

    private long id;
    private String uid;
    private String title;
    private String photoUrl;
    private TournamentPrivacy tournamentPrivacy;
    private boolean tournamentGroups;
    private boolean tournamentTeams;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar creationDate;
    private long userCreationId;
    private TournamentStage tournamentStage;

}
