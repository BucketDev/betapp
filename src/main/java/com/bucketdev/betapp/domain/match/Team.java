package com.bucketdev.betapp.domain.match;

import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.dto.match.TeamDTO;
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

    @NotNull
    @ManyToOne
    private Tournament tournament;

    @Column
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
        if (tournament != null)
            dto.setTournamentId(tournament.getId());

        return dto;
    }

    public void setValuesFromDTO(TeamDTO dto) {
        name = dto.getName();
        uid = dto.getUid();
        photoUrl = dto.getPhotoUrl();
    }

}
