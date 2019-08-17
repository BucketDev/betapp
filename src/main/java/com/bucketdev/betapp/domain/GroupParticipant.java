package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.GroupParticipantDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "group_participants")
@Getter
@Setter
public class GroupParticipant {

    public GroupParticipant(Group group, User participant, int points) {
        this.setGroup(group);
        this.setTournament(group.getTournament());
        this.setUser(participant);
        this.setGamesPlayed(0);
        this.setGamesWon(0);
        this.setGamesTied(0);
        this.setGamesLost(0);
        this.setPoints(points);
    }

    public GroupParticipant() { }

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
    private User user;

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

    public GroupParticipantDTO toDTO() {
        GroupParticipantDTO dto = new GroupParticipantDTO();

        dto.setId(id);
        dto.setGamesPlayed(gamesPlayed);
        dto.setGamesWon(gamesWon);
        dto.setGamesTied(gamesTied);
        dto.setGamesLost(gamesLost);
        dto.setPoints(points);

        if (user != null)
            dto.setUser(user.toDTO());
        if (group != null) {
            dto.setGroupId(group.getId());
            dto.setGroupName(group.getName());
        }
        if (tournament != null)
            dto.setTournamentId(tournament.getId());

        return dto;
    }

    public void setValuesFromDTO(GroupParticipantDTO dto) {
        gamesPlayed = dto.getGamesPlayed();
        gamesWon = dto.getGamesWon();
        gamesTied = dto.getGamesTied();
        gamesLost = dto.getGamesLost();
        points = dto.getPoints();
    }

}
