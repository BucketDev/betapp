package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.TournamentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface TournamentDetailsRepository extends JpaRepository<TournamentDetails, Long> {

    TournamentDetails findByUid(String uid);

}