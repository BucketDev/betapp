package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.PlayoffSettingsDTO;
import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "playoff_settings")
@Getter
@Setter
public class PlayoffSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "tournament_settings_id")
    private TournamentSettings tournamentSettings;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private PlayoffStage playoffStage;

    @Column
    @NotNull
    private boolean roundTrip;

    public PlayoffSettingsDTO toDTO() {
        PlayoffSettingsDTO dto = new PlayoffSettingsDTO();

        dto.setId(id);
        dto.setTournamentSettingsId(tournamentSettings.getId());
        dto.setPlayoffStage(playoffStage);
        dto.setRoundTrip(roundTrip);

        return dto;
    }

}
