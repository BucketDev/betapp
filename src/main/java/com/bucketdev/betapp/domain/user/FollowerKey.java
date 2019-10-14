package com.bucketdev.betapp.domain.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Embeddable
@Data
public class FollowerKey implements Serializable {

    @Column(name = "user_following_id")
    private long userFollowingId;

    @Column(name = "user_followed_id")
    private long userFollowedId;

}
