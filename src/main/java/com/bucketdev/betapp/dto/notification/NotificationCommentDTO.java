package com.bucketdev.betapp.dto.notification;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Data
public class NotificationCommentDTO {

    private long id;
    private long notificationId;
    private UserDTO user;
    private String comment;
    @JsonFormat(timezone = "GMT-06:00", pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Calendar creationDate;

}
