package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.TournamentParticipants;
import com.bucketdev.betapp.dto.TournamentParticipantsDTO;
import com.bucketdev.betapp.repository.TournamentParticipantsRepository;
import com.bucketdev.betapp.service.TournamentParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Component
public class TournamentParticipantsServiceImpl implements TournamentParticipantsService {

    @Autowired
    private TournamentParticipantsRepository repository;

    @Override
    public Set<TournamentParticipantsDTO> findByParticipantUid(String uid) {
        return repository.findByParticipantUid(uid).stream().map(TournamentParticipants::toDTO).collect(Collectors.toSet());
    }
}
