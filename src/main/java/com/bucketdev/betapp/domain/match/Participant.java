package com.bucketdev.betapp.domain.match;

import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.match.ParticipantDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "participants")
@Getter
@Setter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private Tournament tournament;

    @NotNull
    @ManyToOne
    private User user;

    public ParticipantDTO toDTO() {
        ParticipantDTO dto = new ParticipantDTO();

        dto.setId(id);
        if (tournament != null)
            dto.setTournamentId(tournament.getId());
        if (user != null)
            dto.setUser(user.toDTO());

        return dto;
    }

}
