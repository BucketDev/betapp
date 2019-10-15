package com.bucketdev.betapp.dto.notification;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class NotificationDTO implements Serializable {

    private long id;
    private NotificationTypeDTO notificationType;
    private UserDTO userOrigin;
    private String destinyUid;
    private String destinyName;
    private String destinyPhotoUrl;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar creationDate;

}
