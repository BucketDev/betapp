package com.bucketdev.betapp.domain.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "notification_likes")
@Data
@NoArgsConstructor
public class NotificationLikes {

    public NotificationLikes(NotificationLikesKey key) {
        this.notificationLikesKey = key;
    }

    @EmbeddedId
    private NotificationLikesKey notificationLikesKey;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationDate;

}
