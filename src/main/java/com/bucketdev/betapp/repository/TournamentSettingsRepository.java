package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.TournamentSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface TournamentSettingsRepository extends JpaRepository<TournamentSettings, Long> {

    TournamentSettings findByTournamentUid(String uid);

}
