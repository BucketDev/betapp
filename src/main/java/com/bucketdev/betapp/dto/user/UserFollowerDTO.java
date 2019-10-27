package com.bucketdev.betapp.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class UserFollowerDTO implements Serializable {

    private long id;
    private String uid;
    private String displayName;
    private String description;
    private String photoUrl;
    private List<UserDTO> following;
    private List<UserDTO> followers;

}
