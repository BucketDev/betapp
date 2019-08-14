package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.TournamentParticipantsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
public interface TournamentParticipantsService {

    Page<TournamentParticipantsDTO> findByParticipantUid(Pageable page, String uid);

}
