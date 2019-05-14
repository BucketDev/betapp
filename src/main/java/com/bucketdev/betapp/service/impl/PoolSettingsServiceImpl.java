package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.PoolSettings;
import com.bucketdev.betapp.domain.Tournament;
import com.bucketdev.betapp.dto.PoolSettingsDTO;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.repository.PoolSettingsRepository;
import com.bucketdev.betapp.repository.TournamentRepository;
import com.bucketdev.betapp.service.PoolSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author rodrigo.loyola
 */
@Component
public class PoolSettingsServiceImpl implements PoolSettingsService {

    @Autowired
    private PoolSettingsRepository repository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public PoolSettingsDTO upsert(PoolSettingsDTO dto) {
        PoolSettings poolSettings = new PoolSettings();
        Optional<PoolSettings> optionalPoolSettings = repository.findByTournamentId(dto.getTournamentId());
        if(optionalPoolSettings.isPresent())
            poolSettings = optionalPoolSettings.get();
        else {
            Optional<Tournament> optionalTournament = tournamentRepository.findById(dto.getTournamentId());
            if(!optionalTournament.isPresent())
                throw new TournamentNotFoundException("id: " + dto.getTournamentId());
            poolSettings.setTournament(optionalTournament.get());
        }
        poolSettings.setValuesFromDTO(dto);

        return repository.save(poolSettings).toDTO();
    }

}
