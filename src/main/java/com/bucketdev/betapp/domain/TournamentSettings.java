package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.TournamentSettingsDTO;
import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "tournament_settings")
@Getter
@Setter
public class TournamentSettings {

    public TournamentSettings() {
        this.groupNumber = 1;
        this.first = 1;
        this.playoffStage = PlayoffStage.EIGHTH_FINALS;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Column
    @NotNull
    private boolean groupRoundTrip;

    @Column
    @NotNull
    @Min(value = 1)
    @Max(value = 8)
    private int groupNumber;

    @Column
    @NotNull
    @Min(value = 1)
    private int first;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayoffStage playoffStage;

    @OneToMany(mappedBy = "tournamentSettings", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlayoffSettings> playoffSettings;

    public TournamentSettingsDTO toDTO() {
        TournamentSettingsDTO dto = new TournamentSettingsDTO();

        dto.setId(id);
        dto.setTournamentId(tournament.getId());
        dto.setGroupRoundTrip(groupRoundTrip);
        dto.setGroupNumber(groupNumber);
        dto.setFirst(first);
        dto.setPlayoffStage(playoffStage);
        dto.setPlayoffSettings(playoffSettings.stream().map(PlayoffSettings::toDTO).collect(Collectors.toSet()));

        return dto;
    }

    public void setValuesFromDTO(TournamentSettingsDTO dto) {
        groupRoundTrip = dto.isGroupRoundTrip();
        groupNumber = dto.getGroupNumber();
        first = dto.getFirst();
        playoffStage = dto.getPlayoffStage();
    }

}
