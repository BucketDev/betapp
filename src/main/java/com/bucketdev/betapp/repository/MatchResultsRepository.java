package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.MatchResult;
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
