package com.bucketdev.betapp.domain.notification;

import com.bucketdev.betapp.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Embeddable
@Data
public class NotificationReceiverKey implements Serializable {

    @Column(name = "notification_id")
    private long notificationId;

    @Column(name = "user_id")
    private long userId;

}
