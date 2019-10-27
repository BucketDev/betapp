package com.bucketdev.betapp.domain.notification;

import com.bucketdev.betapp.domain.NotificationDestiny;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.notification.NotificationDTO;
import com.bucketdev.betapp.type.NotificationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
public class Notification {

    public Notification(@NotNull NotificationType notificationType, @NotNull User userOrigin, NotificationDestiny destiny) {
        this.notificationType = notificationType;
        this.userOrigin = userOrigin;
        this.destinyUid = destiny.getDestinyUid();
        this.destinyName = destiny.getDestinyName();
        this.destinyPhotoUrl = destiny.getDestinyPhotoUrl();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
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

    @ManyToMany
    @JoinTable(name = "notification_likes",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userLikes;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationDate;

    public NotificationDTO toDTO() {
        NotificationDTO dto = new NotificationDTO();

        dto.setId(id);
        dto.setNotificationType(notificationType);
        dto.setUserOrigin(userOrigin.toDTO());
        dto.setDestinyUid(destinyUid);
        dto.setDestinyName(destinyName);
        dto.setDestinyPhotoUrl(destinyPhotoUrl);
        dto.setCreationDate(creationDate);

        return dto;
    }

}
