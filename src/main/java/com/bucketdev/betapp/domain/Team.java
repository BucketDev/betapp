package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.TeamDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String uid;

    @Column
    private String photoUrl;

    @Column
    @NotNull
    private String name;

    public TeamDTO toDTO() {
        TeamDTO dto = new TeamDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setPhotoUrl(photoUrl);
        dto.setName(name);

        return dto;
    }

}
