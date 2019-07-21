package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.Tournament;
import com.bucketdev.betapp.domain.TournamentSettings;
import com.bucketdev.betapp.dto.TournamentSettingsDTO;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.repository.TournamentRepository;
import com.bucketdev.betapp.repository.TournamentSettingsRepository;
import com.bucketdev.betapp.service.TournamentSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author rodrigo.loyola
 */
@Component
public class TournamentSettingsServiceImpl implements TournamentSettingsService {

    @Autowired
    private TournamentSettingsRepository repository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public TournamentSettingsDTO findByTournamentUid(String uid) {
        TournamentSettings tournamentSettings = repository.findByTournamentUid(uid);
        return tournamentSettings == null ? null : tournamentSettings.toDTO();
    }

    @Override
    public TournamentSettingsDTO upsert(TournamentSettingsDTO dto) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(dto.getTournamentId());
        if (!optionalTournament.isPresent())
            throw new TournamentNotFoundException("id: " + dto.getTournamentId());
        Tournament tournament = optionalTournament.get();

        TournamentSettings tournamentSettings = repository.findByTournamentUid(tournament.getUid());
        if (tournamentSettings == null) {
            tournamentSettings = new TournamentSettings();
        }
        tournamentSettings.setTournament(tournament);
        tournamentSettings.setValuesFromDTO(dto);

        return repository.save(tournamentSettings).toDTO();
    }
}
