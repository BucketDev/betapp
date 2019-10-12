package com.bucketdev.betapp.service.tournament;

import com.bucketdev.betapp.dto.tournament.TournamentParticipantsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author rodrigo.loyola
 */
public interface TournamentParticipantsService {

    Page<TournamentParticipantsDTO> findByParticipantUid(Pageable page, String uid);

}
