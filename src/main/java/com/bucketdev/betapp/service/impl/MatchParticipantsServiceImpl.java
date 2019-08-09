package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.MatchParticipants;
import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.repository.MatchParticipantsRepository;
import com.bucketdev.betapp.service.MatchParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class MatchParticipantsServiceImpl implements MatchParticipantsService {

    @Autowired
    private MatchParticipantsRepository repository;

    @Override
    public List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId) {
        return repository.findAllByTournamentId(tournamentId).stream()
                .map(MatchParticipants::toDTO).collect(Collectors.toList());
    }
}
