package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.GroupTeamDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "group_teams")
@Getter
@Setter
public class GroupTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column
    @Min(value = 0)
    private Integer gamesPlayed;

    @Column
    @Min(value = 0)
    private Integer gamesWon;

    @Column
    @Min(value = 0)
    private Integer gamesTied;

    @Column
    @Min(value = 0)
    private Integer gamesLost;

    @Column
    @Min(value = 0)
    private Integer points;

    public GroupTeamDTO toDTO() {
        GroupTeamDTO dto = new GroupTeamDTO();

        dto.setId(id);
        if (group != null)
            dto.setGroupId(group.getId());
        if (tournament != null)
            dto.setTournamentId(tournament.getId());
        if (team != null)
            dto.setTeam(team.toDTO());
        dto.setGamesPlayed(gamesPlayed);
        dto.setGamesWon(gamesWon);
        dto.setGamesTied(gamesTied);
        dto.setGamesLost(gamesLost);
        dto.setPoints(points);

        return dto;
    }

}
