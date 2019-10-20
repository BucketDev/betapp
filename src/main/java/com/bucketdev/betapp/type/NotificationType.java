package com.bucketdev.betapp.type;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
public enum NotificationType implements Serializable {

    NEW_TOURNAMENT(1, "Tournament creation", "has created a new tournament!");

    private int id;
    private String name;
    private String text;

    NotificationType(int id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

}
