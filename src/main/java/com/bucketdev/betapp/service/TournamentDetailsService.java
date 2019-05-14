package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.TournamentDetailsDTO;

/**
 * @author rodrigo.loyola
 */
public interface TournamentDetailsService {

    TournamentDetailsDTO findByUid(String uid);

}
