package com.bucketdev.betapp.dto.match;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class TeamDTO implements Serializable {

    private long id;
    private long tournamentId;
    private String uid;
    private String photoUrl;
    private String name;

}
