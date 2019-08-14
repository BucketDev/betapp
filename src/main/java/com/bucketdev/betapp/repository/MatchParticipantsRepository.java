package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.MatchParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface MatchParticipantsRepository extends JpaRepository<MatchParticipants, Long> {

    List<MatchParticipants> findAllByTournamentIdAndPlayoffStageIsNull(long tournamentId);

    List<MatchParticipants> findAllByTournamentIdAndPlayoffStageNotNull(long tournamentId);

}
