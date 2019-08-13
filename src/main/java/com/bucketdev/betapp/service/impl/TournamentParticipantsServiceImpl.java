package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.TournamentParticipants;
import com.bucketdev.betapp.dto.TournamentParticipantsDTO;
import com.bucketdev.betapp.repository.TournamentParticipantsRepository;
import com.bucketdev.betapp.service.TournamentParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
