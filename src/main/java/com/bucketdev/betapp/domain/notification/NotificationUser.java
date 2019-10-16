package com.bucketdev.betapp.domain.notification;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.notification.NotificationUserDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity(name = "notification_users")
@Immutable
@Getter
@Setter
public class NotificationUser {

    @EmbeddedId
    private NotificationUserKey notificationUserKey;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "notification_id", insertable = false, updatable = false)
    private Notification notification;

    @Column(name = "user_following_id")
    @NotNull
    private long userFollowingId;


    public NotificationUserDTO toDTO() {
        NotificationUserDTO dto = new NotificationUserDTO();

        dto.setUser(user.toDTO());
        dto.setNotification(notification.toDTO());

        return dto;
    }

}
