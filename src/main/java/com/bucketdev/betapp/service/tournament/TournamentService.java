package com.bucketdev.betapp.service.tournament;

import com.bucketdev.betapp.dto.tournament.TournamentDTO;
import com.bucketdev.betapp.dto.user.UserDTO;

import java.util.List;

public interface TournamentService {

    TournamentDTO save(TournamentDTO dto);
    UserDTO addParticipant(long tournamentId, UserDTO userDTO);
    void deleteParticipants(long tournamentId, List<UserDTO> userDTO);
    TournamentDTO updatePhotoUrl(long id, TournamentDTO dto);
    TournamentDTO updateTournamentStage(TournamentDTO dto);
    void deleteTournament(String uid);
}
