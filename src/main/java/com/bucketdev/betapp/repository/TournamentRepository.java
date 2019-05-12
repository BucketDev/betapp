package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
