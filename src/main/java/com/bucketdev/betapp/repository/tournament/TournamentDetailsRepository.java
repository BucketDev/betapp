package com.bucketdev.betapp.repository.tournament;

import com.bucketdev.betapp.domain.tournament.TournamentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface TournamentDetailsRepository extends JpaRepository<TournamentDetails, Long> {

    TournamentDetails findByUid(String uid);

}
