package com.bucketdev.betapp.service.tournament;

import com.bucketdev.betapp.dto.tournament.TournamentDetailsDTO;

/**
 * @author rodrigo.loyola
 */
public interface TournamentDetailsService {

    TournamentDetailsDTO findByUid(String uid);

}
