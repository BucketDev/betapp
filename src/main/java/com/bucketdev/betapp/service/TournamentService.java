package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.TournamentDTO;
import com.bucketdev.betapp.dto.UserDTO;

public interface TournamentService {

    TournamentDTO save(TournamentDTO dto);
    UserDTO addParticipant(long id, UserDTO userDTO);
    TournamentDTO updatePhotoUrl(long id, TournamentDTO dto);
    TournamentDTO updateTournamentStage(TournamentDTO dto);

}
