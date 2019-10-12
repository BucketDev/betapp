package com.bucketdev.betapp.service.tournament;

import com.bucketdev.betapp.dto.tournament.TournamentDTO;
import com.bucketdev.betapp.dto.user.UserDTO;

public interface TournamentService {

    TournamentDTO save(TournamentDTO dto);
    UserDTO addParticipant(long id, UserDTO userDTO);
    TournamentDTO updatePhotoUrl(long id, TournamentDTO dto);
    TournamentDTO updateTournamentStage(TournamentDTO dto);
    void deleteTournament(String uid);
}
