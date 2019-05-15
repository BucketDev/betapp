package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.TournamentSettingsDTO;

/**
 * @author rodrigo.loyola
 */
public interface TournamentSettingsService {

    TournamentSettingsDTO upsert(TournamentSettingsDTO dto);

}
