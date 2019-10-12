package com.bucketdev.betapp.domain.tournament;

import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.tournament.TournamentParticipantsDTO;
import com.bucketdev.betapp.type.TournamentPrivacy;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "tournaments")
@Getter
@Setter
public class TournamentParticipants {

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

    @NotNull
    @Column
    private Date creationDate;

    @NotNull
    @ManyToOne
    private User userCreation;

    @ManyToOne
    private User userWinner;

    @ManyToMany
    @JoinTable(name = "participants",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private TournamentPrivacy tournamentPrivacy;

    public TournamentParticipantsDTO toDTO() {
        TournamentParticipantsDTO dto = new TournamentParticipantsDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setTitle(title);
        dto.setPhotoUrl(photoUrl);
        dto.setCreationDate(creationDate);
        dto.setUserCreationId(userCreation.getId());
        dto.setParticipantsNumber(participants.size());
        dto.setUserWinner(userWinner != null);
        dto.setTournamentPrivacy(tournamentPrivacy);

        return dto;
    }

}
