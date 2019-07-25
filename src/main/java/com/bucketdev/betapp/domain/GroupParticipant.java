package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.GroupParticipantDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @NotNull
    @ManyToOne
    private Group group;

    @NotNull
    @ManyToOne
    private Tournament tournament;

    @NotNull
    @ManyToOne
    private User user;

    public GroupParticipantDTO toDTO() {
        GroupParticipantDTO dto = new GroupParticipantDTO();

        dto.setId(id);
        if (group != null)
            dto.setGroup(group.toDTO());
        if (user != null)
            dto.setUser(user.toDTO());

        return dto;
    }

}
