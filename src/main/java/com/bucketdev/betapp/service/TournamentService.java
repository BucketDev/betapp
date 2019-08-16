package com.bucketdev.betapp.service;

import com.bucketdev.betapp.domain.Group;
import com.bucketdev.betapp.domain.Tournament;
import com.bucketdev.betapp.dto.TournamentDTO;
import com.bucketdev.betapp.dto.UserDTO;
import com.bucketdev.betapp.type.PlayoffStage;

public interface TournamentService {

    TournamentDTO save(TournamentDTO dto);
    UserDTO addParticipant(long id, UserDTO userDTO);
    TournamentDTO updatePhotoUrl(long id, TournamentDTO dto);
    TournamentDTO updateTournamentStage(TournamentDTO dto);
    void oneTripFinalMatchesPerGroup(Group group, PlayoffStage playoffStage);

    void roundTripFinalMatchesPerGroup(Group group, PlayoffStage playoffStage);
}
