package com.bucketdev.betapp.domain.match;

import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.match.MatchResultDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "match_results")
@Getter
@Setter
@NoArgsConstructor
public class MatchResult {

    public MatchResult(Tournament tournament, MatchTeams matchTeams, User user) {
        this.setTournament(tournament);
        this.setMatchTeams(matchTeams);
        this.setUser(user);
        this.setScoreAway(0);
        this.setScoreHome(0);
        this.setPoints(0);
        this.setCreationTime(Calendar.getInstance());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "match_teams_id")
    private MatchTeams matchTeams;

    @Column
    @Min(value = 0)
    private int scoreAway;

    @Column
    @Min(value = 0)
    private int scoreHome;

    @Column
    @Min(value = 0)
    private int points;

    @Column
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationTime;

    public MatchResultDTO toDTO() {
        MatchResultDTO dto = new MatchResultDTO();

        dto.setId(id);
        if (user != null)
            dto.setUserId(user.getId());
        if (tournament != null)
            dto.setTournamentId(tournament.getId());
        if (matchTeams != null)
            dto.setMatchTeamsId(matchTeams.getId());
        dto.setScoreAway(scoreAway);
        dto.setScoreHome(scoreHome);
        dto.setPoints(points);
        dto.setCreationTime(creationTime);

        return dto;
    }

}
