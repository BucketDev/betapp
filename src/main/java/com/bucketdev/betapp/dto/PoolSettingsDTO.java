package com.bucketdev.betapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class PoolSettingsDTO {

    private long id;
    private long tournamentId;
    private float balance;
    private String description;
    private float amount;
    private float goal;
    private Date limitDate;

}
