package com.bucketdev.betapp.repository.tournament;

import com.bucketdev.betapp.domain.tournament.PoolSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface PoolSettingsRepository extends JpaRepository<PoolSettings, Long> {

    Optional<PoolSettings> findByTournamentId(long tournamentId);

}
