package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.dto.MatchTeamsDTO;
import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Set;

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
    @NotNull
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "group_team_away_id")
    private GroupTeam groupTeamAway;

    @Column
    @Min(value = 0)
    private int scoreAway;

    @ManyToOne
    @JoinColumn(name = "group_team_home_id")
    private GroupTeam groupTeamHome;

    @Column
    @Min(value = 0)
    private int scoreHome;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar scheduledTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registeredTime;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayoffStage playoffStage;

    @Column
    @NotNull
    private int round;

    public MatchTeamsDTO toDTO() {
        MatchTeamsDTO dto = new MatchTeamsDTO();

        dto.setId(id);
        if (tournament != null)
            dto.setTournamentId(tournament.getId());
        if (groupTeamAway != null)
            dto.setGroupTeamAway(groupTeamAway.toDTO());
        dto.setScoreAway(scoreAway);
        if (groupTeamHome != null)
            dto.setGroupTeamHome(groupTeamHome.toDTO());
        dto.setScoreHome(scoreHome);
        dto.setScheduledTime(scheduledTime);
        dto.setRegisteredTime(registeredTime);
        dto.setPlayoffStage(playoffStage);
        dto.setRound(round);

        return dto;
    }

}
