package com.bucketdev.betapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "match_teams")
@Getter
@Setter
public class MatchTeams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "group_team_away_id")
    private GroupTeam groupTeamAway;

    @Column
    @Min(value = 0)
    private Integer scoreAway;

    @ManyToOne
    @JoinColumn(name = "group_team_home_id")
    private GroupTeam groupTeamHome;

    @Column
    @Min(value = 0)
    private Integer scoreHome;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar scheduledTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registeredTime;

}
