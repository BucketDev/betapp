package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.GroupParticipant;
import com.bucketdev.betapp.domain.MatchParticipants;
import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.exception.matchParticipants.MatchParticipantsNotFoundException;
import com.bucketdev.betapp.repository.GroupParticipantRepository;
import com.bucketdev.betapp.repository.MatchParticipantsRepository;
import com.bucketdev.betapp.service.MatchParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class MatchParticipantsServiceImpl implements MatchParticipantsService {

    @Autowired
    private MatchParticipantsRepository repository;

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Override
    public List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId) {
        return repository.findAllByTournamentId(tournamentId).stream()
                .map(MatchParticipants::toDTO).collect(Collectors.toList());
    }

    @Override
    public MatchParticipantsDTO update(MatchParticipantsDTO dto) {
        Optional<MatchParticipants> optionalMatchParticipants = repository.findById(dto.getId());
        if (!optionalMatchParticipants.isPresent())
            throw new MatchParticipantsNotFoundException("id: " + dto.getId());
        MatchParticipants matchParticipants = optionalMatchParticipants.get();

        matchParticipants.setScoreAway(dto.getScoreAway());
        matchParticipants.setScoreHome(dto.getScoreHome());
        matchParticipants.setRegisteredTime(Calendar.getInstance());

        calculatePoints(matchParticipants);

        return repository.save(matchParticipants).toDTO();
    }

    private void calculatePoints(MatchParticipants matchParticipants) {
        int scoreAway = matchParticipants.getScoreAway();
        int scoreHome = matchParticipants.getScoreHome();
        GroupParticipant participantAway = matchParticipants.getGroupParticipantAway();
        GroupParticipant participantHome = matchParticipants.getGroupParticipantHome();
        participantAway.setGamesPlayed(participantAway.getGamesPlayed() + 1);
        participantHome.setGamesPlayed(participantHome.getGamesPlayed() + 1);
        if (scoreAway > scoreHome) {
            participantAway.setGamesWon(participantAway.getGamesWon() + 1);
            participantAway.setPoints(participantAway.getPoints() + 3);
            participantHome.setGamesLost(participantHome.getGamesLost() + 1);
        } else if (scoreHome > scoreAway) {
            participantHome.setGamesWon(participantHome.getGamesWon() + 1);
            participantHome.setPoints(participantHome.getPoints() + 3);
            participantAway.setGamesLost(participantAway.getGamesLost() + 1);
        } else {
            participantAway.setGamesTied(participantAway.getGamesTied() + 1);
            participantAway.setPoints(participantAway.getPoints() + 1);
            participantHome.setGamesTied(participantHome.getGamesTied() + 1);
            participantHome.setPoints(participantHome.getPoints() + 1);
        }
    }


}
