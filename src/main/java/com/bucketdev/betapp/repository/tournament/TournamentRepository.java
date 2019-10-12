package com.bucketdev.betapp.repository.tournament;

import com.bucketdev.betapp.domain.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Tournament findByUid(String uid);

}
