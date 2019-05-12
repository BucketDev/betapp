package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.TournamentParticipantsDTO;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
public interface TournamentParticipantsService {

    Set<TournamentParticipantsDTO> findByParticipantUid(String uid);

}
