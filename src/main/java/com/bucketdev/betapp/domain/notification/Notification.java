package com.bucketdev.betapp.domain.notification;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.notification.NotificationDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType notificationType;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_origin_id")
    private User userOrigin;

    @Column
    @NotNull
    private String destinyUid;

    @Column
    @NotNull
    private String destinyName;

    @Column
    @NotNull
    private String destinyPhotoUrl;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationDate;

    public NotificationDTO toDTO() {
        NotificationDTO dto = new NotificationDTO();

        dto.setId(id);
        dto.setNotificationType(notificationType.toDTO());
        dto.setUserOrigin(userOrigin.toDTO());
        dto.setDestinyUid(destinyUid);
        dto.setDestinyName(destinyName);
        dto.setDestinyPhotoUrl(destinyPhotoUrl);
        dto.setCreationDate(creationDate);

        return dto;
    }

}
