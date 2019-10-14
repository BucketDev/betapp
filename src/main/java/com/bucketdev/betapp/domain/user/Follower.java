package com.bucketdev.betapp.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "followers")
@Getter
@Setter
public class Follower {

    @EmbeddedId
    private FollowerKey followerKey;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationDate;

}
