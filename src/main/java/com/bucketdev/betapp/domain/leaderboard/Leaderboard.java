package com.bucketdev.betapp.domain.leaderboard;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.leaderboard.LeaderboardDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "leaderboards")
@Immutable
@Getter
@Setter
public class Leaderboard {

    @EmbeddedId
    private LeaderboardKey leaderboardKey;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(columnDefinition = "INT")
    private long points;

    public LeaderboardDTO toDTO() {
        LeaderboardDTO dto = new LeaderboardDTO();

        dto.setUser(user.toDTO());
        dto.setPoints(points);

        return dto;
    }

}
