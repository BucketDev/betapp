package com.bucketdev.betapp.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class UserFollowerCountDTO implements Serializable {

    private long id;
    private String uid;
    private String displayName;
    private String photoUrl;
    private int following;
    private int followers;

}
