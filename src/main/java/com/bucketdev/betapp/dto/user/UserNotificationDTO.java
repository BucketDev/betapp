package com.bucketdev.betapp.dto.user;

import com.bucketdev.betapp.dto.notification.NotificationDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class UserNotificationDTO implements Serializable {

    private long id;
    private String uid;
    private Set<NotificationDTO> notifications;

}
