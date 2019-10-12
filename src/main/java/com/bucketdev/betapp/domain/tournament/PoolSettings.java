package com.bucketdev.betapp.domain.tournament;

import com.bucketdev.betapp.dto.tournament.PoolSettingsDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "pool_settings")
@Getter
@Setter
public class PoolSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Column
    private float balance;

    @Column
    private String description;

    @Column
    private float amount;

    @Column
    private float goal;

    @Column
    @Temporal(TemporalType.DATE)
    private Calendar limitDate;

    public PoolSettingsDTO toDTO() {
        PoolSettingsDTO dto = new PoolSettingsDTO();

        dto.setId(id);
        dto.setTournamentId(tournament.getId());
        dto.setBalance(balance);
        dto.setDescription(description);
        dto.setAmount(amount);
        dto.setGoal(goal);
        dto.setLimitDate(limitDate);

        return dto;
    }

    public void setValuesFromDTO(PoolSettingsDTO dto) {
        balance = dto.getBalance();
        description = dto.getDescription();
        amount = dto.getAmount();
        goal = dto.getGoal();
        limitDate = dto.getLimitDate();
    }

}
