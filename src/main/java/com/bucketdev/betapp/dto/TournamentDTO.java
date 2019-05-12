package com.bucketdev.betapp.dto;

import com.bucketdev.betapp.type.TournamentPrivacy;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class TournamentDTO {

    private long tournamentId;
    private String uid;
    private String title;
    private String photoUrl;
    private TournamentPrivacy tournamentPrivacy;
    private boolean tournamentGroups;
    private boolean tournamentTeams;
    private Date creationDate;
    private long userCreationId;

}
