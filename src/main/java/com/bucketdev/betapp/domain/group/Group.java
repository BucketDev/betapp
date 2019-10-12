package com.bucketdev.betapp.domain.group;

import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.dto.group.GroupDTO;
import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "groups")
@Getter
@Setter
public class Group {

    public Group(char _name) {
        name = _name;
    }

    public Group() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private char name;

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayoffStage playoffStage;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("points DESC, gamesWon DESC")
    private List<GroupParticipant> groupParticipants;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("points DESC, gamesWon DESC")
    private List<GroupTeam> groupTeams;

    public GroupDTO toDTO() {
        GroupDTO dto = new GroupDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setTournamentId(tournament.getId());
        dto.setPlayoffStage(playoffStage);
        dto.setGroupParticipants(groupParticipants.stream().map(GroupParticipant::toDTO).collect(Collectors.toList()));
        dto.setGroupTeams(groupTeams.stream().map(GroupTeam::toDTO).collect(Collectors.toList()));

        return dto;
    }

}


