package com.bucketdev.betapp.dto.notification;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class NotificationTypeDTO implements Serializable {

    private long id;
    private String name;
    private String text;

}
