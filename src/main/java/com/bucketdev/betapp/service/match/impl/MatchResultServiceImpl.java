package com.bucketdev.betapp.service.match.impl;

import com.bucketdev.betapp.domain.match.MatchResult;
import com.bucketdev.betapp.domain.match.MatchTeams;
import com.bucketdev.betapp.domain.match.Participant;
import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.match.MatchResultDTO;
import com.bucketdev.betapp.dto.match.ParticipantResultsDTO;
import com.bucketdev.betapp.exception.match.MatchResultNotFoundException;
import com.bucketdev.betapp.exception.match.MatchTeamsNotFoundException;
import com.bucketdev.betapp.exception.tournament.TournamentNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.match.MatchResultsRepository;
import com.bucketdev.betapp.repository.match.MatchTeamsRepository;
import com.bucketdev.betapp.repository.match.ParticipantRepository;
import com.bucketdev.betapp.repository.tournament.TournamentRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.match.MatchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class MatchResultServiceImpl implements MatchResultService {

    enum MatchResultType {
        AWAY_LOSE(-1),
        TIE(0),
        AWAY_WIN(1);

        private int value;

        MatchResultType(int value) {
            this.value = value;
        }
    }

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
            if (!optionalUser.isPresent())
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
        return participantResults.stream()
                .sorted(Comparator.comparing(participantResultsDTO ->
                                participantResultsDTO.getMatchResult() == null ? null : participantResultsDTO.getMatchResult().getCreationTime(),
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void calculateBetPoints(MatchTeams matchTeams) {
        Set<MatchResult> matchResults = repository.findAllByMatchTeamsId(matchTeams.getId());
        matchResults.forEach(matchResult -> {
            int points = 0;
            if (matchResult.getScoreAway() == matchTeams.getScoreAway() && matchResult.getScoreHome() == matchTeams.getScoreHome())
                points += 2;
            if (getMatchTypeResult(matchResult.getScoreAway(), matchResult.getScoreHome())
                    .equals(getMatchTypeResult(matchTeams.getScoreAway(), matchTeams.getScoreHome())))
                points += 1;
            matchResult.setPoints(points);
        });
        repository.saveAll(matchResults);
    }

    private MatchResultType getMatchTypeResult(int awayScore, int homeScore) {
        if (awayScore > homeScore)
            return MatchResultType.AWAY_WIN;
        else if (awayScore < homeScore)
            return MatchResultType.AWAY_LOSE;
        return MatchResultType.TIE;
    }
}
