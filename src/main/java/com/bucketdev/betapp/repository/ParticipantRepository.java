package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p LEFT JOIN GroupParticipant gp ON gp.user.id = p.user.id " +
            "WHERE p.tournament.id = :tournamentId AND gp.id IS NULL")
    List<Participant> pendingForGroupByTournament(@Param("tournamentId") long tournamentId);

}
