package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.GroupDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


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

    /*@ManyToMany
    @JoinTable(name = "group_participants",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> groupParticipants;*/

    public GroupDTO toDTO() {
        GroupDTO dto = new GroupDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setTournamentId(tournament.getId());
        //dto.setGroupParticipants(groupParticipants.stream().map(User::toDTO).collect(Collectors.toSet()));

        return dto;
    }

}


