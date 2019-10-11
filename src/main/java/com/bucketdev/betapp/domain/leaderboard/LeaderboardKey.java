package com.bucketdev.betapp.domain.leaderboard;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Embeddable
public class LeaderboardKey implements Serializable {

    @Column
    private long tournamentId;

    @Column(name = "user_id")
    private long userId;

}
