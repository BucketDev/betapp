package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
}
