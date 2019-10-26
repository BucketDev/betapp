package com.bucketdev.betapp.dto.notification;

import com.bucketdev.betapp.dto.notification.NotificationDTO;
import com.bucketdev.betapp.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class NotificationUserDTO implements Serializable {

    private UserDTO user;
    private NotificationDTO notification;
    private int likes;
    private boolean liked;

}
