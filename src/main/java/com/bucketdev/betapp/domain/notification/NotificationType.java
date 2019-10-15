package com.bucketdev.betapp.domain.notification;

import com.bucketdev.betapp.dto.notification.NotificationTypeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "notification_types")
@Getter
@Setter
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String text;

    public NotificationTypeDTO toDTO() {
        NotificationTypeDTO dto = new NotificationTypeDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setText(text);

        return dto;
    }

}
