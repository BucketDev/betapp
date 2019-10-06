package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.GroupTeamDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class GroupTeam {

    public GroupTeam(Group group, Team team, int points) {
        this.setGroup(group);
        this.setTournament(group.getTournament());
        this.setTeam(team);
        this.setGamesPlayed(0);
        this.setGamesWon(0);
        this.setGamesTied(0);
        this.setGamesLost(0);
        this.setPoints(points);
    }

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
        dto.setGamesPlayed(gamesPlayed);
        dto.setGamesWon(gamesWon);
        dto.setGamesTied(gamesTied);
        dto.setGamesLost(gamesLost);
        dto.setPoints(points);

        if (team != null)
            dto.setTeam(team.toDTO());
        if (group != null) {
            dto.setGroupId(group.getId());
            dto.setGroupName(group.getName());
        }
        if (tournament != null)
            dto.setTournamentId(tournament.getId());

        return dto;
    }

}
