package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.PoolSettingsDTO;

/**
 * @author rodrigo.loyola
 */
public interface PoolSettingsService {

    PoolSettingsDTO upsert(PoolSettingsDTO dto);

}
