package com.bucketdev.betapp.repository.match;

import com.bucketdev.betapp.domain.match.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface MatchResultsRepository extends JpaRepository<MatchResult, Long> {

    MatchResult findByMatchTeamsIdAndUserUid(long matchTeamsId, String userId);

    Set<MatchResult> findAllByMatchTeamsId(long matchTeamId);

}
