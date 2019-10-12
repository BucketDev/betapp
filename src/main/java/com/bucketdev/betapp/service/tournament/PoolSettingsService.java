package com.bucketdev.betapp.service.tournament;

import com.bucketdev.betapp.dto.tournament.PoolSettingsDTO;

/**
 * @author rodrigo.loyola
 */
public interface PoolSettingsService {

    PoolSettingsDTO upsert(PoolSettingsDTO dto);

}
