package com.bucketdev.betapp.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Data
public class UserDetailsDTO implements Serializable {

    private long id;
    private String uid;
    private String displayName;
    private String description;
    private String photoUrl;
    private boolean isFollowing;
    private int followingCount;
    private int followersCount;

}
