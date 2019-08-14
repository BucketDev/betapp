package com.bucketdev.betapp.service;

import com.bucketdev.betapp.domain.TournamentSettings;
import com.bucketdev.betapp.dto.TournamentSettingsDTO;

/**
 * @author rodrigo.loyola
 */
public interface TournamentSettingsService {

    TournamentSettingsDTO findByTournamentUid(String uid);
    TournamentSettingsDTO upsert(TournamentSettingsDTO dto);
    void generateFinalsGroups(TournamentSettings tournamentSettings);

}
