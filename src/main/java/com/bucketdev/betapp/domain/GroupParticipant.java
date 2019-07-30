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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @NotNull
    @ManyToOne
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

    public GroupParticipantDTO toDTO() {
        GroupParticipantDTO dto = new GroupParticipantDTO();

        dto.setId(id);
        dto.setGamesPlayed(gamesPlayed);
        dto.setGamesWon(gamesWon);
        dto.setGamesTied(gamesTied);
        dto.setGamesLost(gamesLost);

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

}
