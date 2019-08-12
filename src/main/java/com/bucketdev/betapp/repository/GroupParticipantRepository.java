package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.GroupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {

    List<GroupParticipant> findAllByTournamentIdOrderByPointsDescGamesWonDesc(long tournamentId);

}
