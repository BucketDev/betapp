package com.bucketdev.betapp.dto;

import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class PlayoffSettingsDTO implements Serializable {

    private long id;
    private long tournamentSettingsId;
    private PlayoffStage playoffStage;
    private boolean roundTrip;

}
