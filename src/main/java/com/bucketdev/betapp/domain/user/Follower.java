package com.bucketdev.betapp.domain.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "followers")
@Data
public class Follower {

    @EmbeddedId
    private FollowerKey followerKey;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationDate;

}
