package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.GroupDTO;
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


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupParticipant> groupParticipants;

    public GroupDTO toDTO() {
        GroupDTO dto = new GroupDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setTournamentId(tournament.getId());
        dto.setGroupParticipants(groupParticipants.stream().map(GroupParticipant::toDTO).collect(Collectors.toList()));

        return dto;
    }

}


