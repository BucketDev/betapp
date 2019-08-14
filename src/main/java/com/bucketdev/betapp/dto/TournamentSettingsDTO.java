package com.bucketdev.betapp.dto;

import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class TournamentSettingsDTO implements Serializable {

    private long id;
    private long tournamentId;
    private boolean groupRoundTrip;
    private boolean eightFinalsRoundTrip;
    private boolean quarterFinalsRoundTrip;
    private boolean semiFinalsRoundTrip;
    private boolean finalRoundTrip;
    private int groupNumber;
    private int first;
    private PlayoffStage playoffStage;

}
