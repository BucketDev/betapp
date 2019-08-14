package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.dto.TournamentDetailsDTO;
import com.bucketdev.betapp.repository.TournamentDetailsRepository;
import com.bucketdev.betapp.service.TournamentDetailsService;
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
