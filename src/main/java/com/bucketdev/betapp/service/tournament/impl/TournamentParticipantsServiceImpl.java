package com.bucketdev.betapp.service.tournament.impl;

import com.bucketdev.betapp.domain.tournament.TournamentParticipants;
import com.bucketdev.betapp.dto.tournament.TournamentParticipantsDTO;
import com.bucketdev.betapp.repository.tournament.TournamentParticipantsRepository;
import com.bucketdev.betapp.service.tournament.TournamentParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author rodrigo.loyola
 */
@Service
public class TournamentParticipantsServiceImpl implements TournamentParticipantsService {

    @Autowired
    private TournamentParticipantsRepository repository;

    @Override
    public Page<TournamentParticipantsDTO> findByParticipantUid(Pageable page, String uid) {
        return repository.findByParticipantUid(page, uid).map(TournamentParticipants::toDTO);
    }
}
