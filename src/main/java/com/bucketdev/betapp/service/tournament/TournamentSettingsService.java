package com.bucketdev.betapp.service.tournament;

import com.bucketdev.betapp.domain.tournament.TournamentSettings;
import com.bucketdev.betapp.dto.tournament.TournamentSettingsDTO;

/**
 * @author rodrigo.loyola
 */
public interface TournamentSettingsService {

    TournamentSettingsDTO findByTournamentUid(String uid);
    TournamentSettingsDTO upsert(TournamentSettingsDTO dto);
    void generateFinalsGroups(TournamentSettings tournamentSettings);

}
