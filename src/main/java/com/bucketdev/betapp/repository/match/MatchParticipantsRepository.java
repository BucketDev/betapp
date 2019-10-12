package com.bucketdev.betapp.repository.match;

import com.bucketdev.betapp.domain.match.MatchParticipants;
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

    List<MatchParticipants> findByGroupId(long groupId);

}
