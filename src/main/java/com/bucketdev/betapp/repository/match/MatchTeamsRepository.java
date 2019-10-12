package com.bucketdev.betapp.repository.match;

import com.bucketdev.betapp.domain.match.MatchTeams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface MatchTeamsRepository extends JpaRepository<MatchTeams, Long> {

    List<MatchTeams> findAllByTournamentIdAndPlayoffStageIsNull(long tournamentId);

    List<MatchTeams> findAllByTournamentIdAndPlayoffStageNotNull(long tournamentId);

    List<MatchTeams> findByGroupId(long groupId);

}
