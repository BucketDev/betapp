package com.bucketdev.betapp.service.match.impl;

import com.bucketdev.betapp.domain.match.Participant;
import com.bucketdev.betapp.dto.match.ParticipantDTO;
import com.bucketdev.betapp.repository.match.ParticipantRepository;
import com.bucketdev.betapp.service.match.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    @Override
    public List<ParticipantDTO> pendingGroupByTournament(long tournamentId) {
        return repository.pendingForGroupByTournament(tournamentId).stream()
                .map(Participant::toDTO).collect(Collectors.toList());
    }

}
