package com.bucketdev.betapp.domain.notification;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Embeddable
@Data
public class NotificationUserKey implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @NaturalId
    @Column(name = "notification_id")
    private long notification_id;

}