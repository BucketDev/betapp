package com.bucketdev.betapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class UserDTO {

    private long userId;
    private String uid;
    private String email;
    private String displayName;
    private String photoUrl;

}
