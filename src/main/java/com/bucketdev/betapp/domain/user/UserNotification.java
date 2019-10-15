package com.bucketdev.betapp.domain.user;

import com.bucketdev.betapp.domain.notification.Notification;
import com.bucketdev.betapp.dto.user.UserNotificationDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String uid;

    @ManyToMany
    @JoinTable(name = "notification_receivers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private Set<Notification> notifications;

    public UserNotificationDTO toDTO() {
        UserNotificationDTO dto = new UserNotificationDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setNotifications(notifications.stream().map(Notification::toDTO).collect(Collectors.toSet()));

        return dto;
    }

}
