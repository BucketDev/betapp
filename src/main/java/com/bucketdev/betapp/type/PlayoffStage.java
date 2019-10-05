package com.bucketdev.betapp.type;

import lombok.Getter;

/**
 * @author rodrigo.loyola
 */
@Getter
public enum PlayoffStage {
    EIGHTH_FINALS(1),
    QUARTER_FINALS(2),
    SEMIFINALS(3),
    FINALS(4);

    private int order;

    PlayoffStage(int order) {
        this.order = order;
    }
}
