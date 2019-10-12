package com.bucketdev.betapp.service.tournament.impl;

import com.bucketdev.betapp.dto.tournament.TournamentDetailsDTO;
import com.bucketdev.betapp.repository.tournament.TournamentDetailsRepository;
import com.bucketdev.betapp.service.tournament.TournamentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rodrigo.loyola
 */
@Service
public class TournamentDetailsServiceImpl implements TournamentDetailsService {

    @Autowired
    private TournamentDetailsRepository repository;

    @Override
    public TournamentDetailsDTO findByUid(String uid) {
        return repository.findByUid(uid).toDTO();
    }

}
