package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.TournamentDetailsDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author
 */
@Entity
@Table(name = "tournaments")
@Getter
@Setter
public class TournamentDetails {

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
    @Temporal(value = TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    private User userCreation;

    @ManyToMany
    @JoinTable(name = "participants",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;

    @OneToOne(mappedBy = "tournament", cascade = CascadeType.ALL, optional = false)
    private PoolSettings poolSettings;

    public TournamentDetailsDTO toDTO() {
        TournamentDetailsDTO dto = new TournamentDetailsDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setTitle(title);
        dto.setPhotoUrl(photoUrl);
        dto.setCreationDate(creationDate);
        dto.setUserCreationId(userCreation.getId());
        dto.setParticipants(participants.stream().map(User::toDTO).collect(Collectors.toSet()));
        if(poolSettings != null)
            dto.setPoolSettings(poolSettings.toDTO());

        return dto;
    }

}
