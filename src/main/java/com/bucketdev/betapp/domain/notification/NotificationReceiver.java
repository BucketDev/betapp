package com.bucketdev.betapp.domain.notification;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "notification_receivers")
@Getter
@Setter
public class NotificationReceiver {

    @EmbeddedId
    private NotificationReceiverKey notificationReceiverKey;

}
