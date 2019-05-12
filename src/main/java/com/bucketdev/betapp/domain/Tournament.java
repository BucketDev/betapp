package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.TournamentDTO;
import com.bucketdev.betapp.type.TournamentPrivacy;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author rodrigo.loyola
 */
@Entity
@Getter
@Setter
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private String uid;

    @NotNull
    @Column
    private String title;

    @Column
    private String photoUrl;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private TournamentPrivacy tournamentPrivacy;

    @NotNull
    @Column
    private boolean tournamentGroups;

    @NotNull
    @Column
    private boolean tournamentTeams;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    private User userCreation;

    public TournamentDTO toDTO() {
        TournamentDTO dto = new TournamentDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setTitle(title);
        dto.setPhotoUrl(photoUrl);
        dto.setTournamentPrivacy(tournamentPrivacy);
        dto.setCreationDate(creationDate);
        dto.setTournamentGroups(tournamentGroups);
        dto.setTournamentTeams(tournamentTeams);
        dto.setUserCreationId(userCreation.getId());

        return dto;
    }

    public void setValuesFromDTO(TournamentDTO dto) {
        title = dto.getTitle();
        photoUrl = dto.getPhotoUrl();
        tournamentPrivacy = dto.getTournamentPrivacy();
        tournamentGroups = dto.isTournamentGroups();
        tournamentTeams = dto.isTournamentTeams();
    }

}
