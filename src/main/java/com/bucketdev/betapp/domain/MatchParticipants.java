package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "match_participants")
@Getter
@Setter
public class MatchParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "group_participant_away_id")
    private GroupParticipant groupParticipantAway;

    @Column
    @Min(value = 0)
    private int scoreAway;

    @ManyToOne
    @JoinColumn(name = "group_participant_home_id")
    private GroupParticipant groupParticipantHome;

    @Column
    @Min(value = 0)
    private int scoreHome;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar scheduledTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registeredTime;

    public MatchParticipantsDTO toDTO() {
        MatchParticipantsDTO dto = new MatchParticipantsDTO();

        dto.setId(id);
        if (tournament != null)
            dto.setTournamentId(tournament.getId());
        if (groupParticipantAway != null)
            dto.setGroupParticipantAway(groupParticipantAway.toDTO());
        dto.setScoreAway(scoreAway);
        if (groupParticipantHome != null)
            dto.setGroupParticipantHome(groupParticipantHome.toDTO());
        dto.setScoreHome(scoreHome);
        dto.setScheduledTime(scheduledTime);
        dto.setRegisteredTime(registeredTime);

        return dto;
    }

}
