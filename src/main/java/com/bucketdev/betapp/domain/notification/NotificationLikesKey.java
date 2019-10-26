package com.bucketdev.betapp.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLikesKey implements Serializable {

    @NaturalId
    @Column(name = "notification_id")
    private long notificationId;

    @NaturalId
    @Column(name = "user_id")
    private long userId;

}
