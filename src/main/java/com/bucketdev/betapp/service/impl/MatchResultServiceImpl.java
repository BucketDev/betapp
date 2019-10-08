package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.*;
import com.bucketdev.betapp.dto.MatchResultDTO;
import com.bucketdev.betapp.dto.ParticipantResultsDTO;
import com.bucketdev.betapp.exception.matchResult.MatchResultNotFoundException;
import com.bucketdev.betapp.exception.matchTeams.MatchTeamsNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.*;
import com.bucketdev.betapp.service.MatchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class MatchResultServiceImpl implements MatchResultService {

    @Autowired
    private MatchResultsRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MatchTeamsRepository matchTeamsRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public MatchResultDTO upsert(MatchResultDTO dto) {
        MatchResult matchResult = new MatchResult();
        if (dto.getId() > 0) {
            Optional<MatchResult> optionalMatchResult = repository.findById(dto.getId());
            if (!optionalMatchResult.isPresent())
                throw new MatchResultNotFoundException("id: " + dto.getId());
            matchResult = optionalMatchResult.get();
        } else {
            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
            if(!optionalUser.isPresent())
                throw new UserNotFoundException("id: " + dto.getUserId());
            User user = optionalUser.get();
            Optional<Tournament> optionalTournament = tournamentRepository.findById(dto.getTournamentId());
            if (!optionalTournament.isPresent())
                throw new TournamentNotFoundException("id: " + dto.getTournamentId());
            Tournament tournament = optionalTournament.get();
            Optional<MatchTeams> optionalMatchTeams = matchTeamsRepository.findById(dto.getMatchTeamsId());
            if (!optionalMatchTeams.isPresent())
                throw new MatchTeamsNotFoundException("id: " + dto.getMatchTeamsId());
            MatchTeams matchTeams = optionalMatchTeams.get();
            matchResult.setTournament(tournament);
            matchResult.setMatchTeams(matchTeams);
            matchResult.setUser(user);
        }
        matchResult.setScoreAway(dto.getScoreAway());
        matchResult.setScoreHome(dto.getScoreHome());
        matchResult.setPoints(dto.getPoints());
        matchResult.setCreationTime(Calendar.getInstance());

        return repository.save(matchResult).toDTO();
    }

    @Override
    public Set<ParticipantResultsDTO> findByMatchTeamId(long matchTeamId) {
        Optional<MatchTeams> optionalMatchTeams = matchTeamsRepository.findById(matchTeamId);
        if (!optionalMatchTeams.isPresent())
            throw new MatchTeamsNotFoundException("id: " + matchTeamId);
        MatchTeams matchTeams = optionalMatchTeams.get();
        Set<Participant> participants =
                participantRepository.findAllByTournamentId(matchTeams.getTournament().getId());
        Set<MatchResult> matchResults = repository.findAllByMatchTeamsId(matchTeamId);
        Set<ParticipantResultsDTO> participantResults = new HashSet<>();

        participants.forEach(participant -> {
            ParticipantResultsDTO dto = new ParticipantResultsDTO();
            dto.setUser(participant.getUser().toDTO());
            matchResults.stream()
                    .filter(_matchResult -> _matchResult.getUser().getId() == participant.getUser().getId())
                    .findFirst().ifPresent(matchResult -> dto.setMatchResult(matchResult.toDTO()));
            participantResults.add(dto);
        });

        return participantResults;
    }
}
