package com.bucketdev.betapp.dto;

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
public class TournamentDetailsDTO implements Serializable {

    private long id;
    private String uid;
    private String title;
    private String photoUrl;
    private boolean tournamentGroups;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar creationDate;
    private long userCreationId;
    private Set<UserDTO> participants;
    private PoolSettingsDTO poolSettings;
    private TournamentSettingsDTO tournamentSettings;

}
