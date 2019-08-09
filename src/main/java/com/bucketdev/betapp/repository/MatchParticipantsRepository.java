package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.MatchParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface MatchParticipantsRepository extends JpaRepository<MatchParticipants, Long> {
}
