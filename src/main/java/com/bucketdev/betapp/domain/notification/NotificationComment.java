package com.bucketdev.betapp.domain.notification;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.notification.NotificationCommentDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "notification_comments")
@Data
public class NotificationComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String comment;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    public NotificationCommentDTO toDTO() {
        NotificationCommentDTO dto = new NotificationCommentDTO();

        dto.setId(id);
        if (user != null)
            dto.setUser(user.toDTO());
        if (notification != null)
            dto.setNotificationId(notification.getId());
        dto.setComment(comment);
        dto.setCreationDate(creationDate);

        return dto;
    }

}
