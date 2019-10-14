package com.bucketdev.betapp.service.match.impl;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.domain.group.GroupTeam;
import com.bucketdev.betapp.domain.leaderboard.Leaderboard;
import com.bucketdev.betapp.domain.match.MatchResult;
import com.bucketdev.betapp.domain.match.MatchTeams;
import com.bucketdev.betapp.domain.match.Team;
import com.bucketdev.betapp.domain.tournament.Tournament;
import com.bucketdev.betapp.domain.tournament.TournamentSettings;
import com.bucketdev.betapp.dto.match.MatchResultDTO;
import com.bucketdev.betapp.dto.match.MatchTeamsDTO;
import com.bucketdev.betapp.exception.match.MatchTeamsNotFoundException;
import com.bucketdev.betapp.repository.group.GroupRepository;
import com.bucketdev.betapp.repository.group.GroupTeamRepository;
import com.bucketdev.betapp.repository.leaderboard.LeaderboardRepository;
import com.bucketdev.betapp.repository.match.MatchResultsRepository;
import com.bucketdev.betapp.repository.match.MatchTeamsRepository;
import com.bucketdev.betapp.repository.tournament.TournamentSettingsRepository;
import com.bucketdev.betapp.service.match.MatchResultService;
import com.bucketdev.betapp.service.match.MatchTeamsService;
import com.bucketdev.betapp.type.PlayoffStage;
import com.bucketdev.betapp.type.TournamentStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author rodrigo.loyola
 */
@Service
public class MatchTeamsServiceImpl implements MatchTeamsService {

    @Autowired
    private MatchTeamsRepository repository;

    @Autowired
    private TournamentSettingsRepository tournamentSettingsRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupTeamRepository groupTeamRepository;

    @Autowired
    private MatchResultsRepository matchResultsRepository;

    @Autowired
    private MatchResultService matchResultService;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Override
    public Map<Integer, List<MatchTeamsDTO>> findAllByTournamentId(long tournamentId, String userUid) {
        Map<Integer, List<MatchTeamsDTO>> rounds = new HashMap<>();
        repository.findAllByTournamentIdAndPlayoffStageIsNull(tournamentId).forEach(matchTeams -> {
            int round = matchTeams.getRound();
            List<MatchTeamsDTO> matches = rounds.get(round);

            if (matches == null) matches = new ArrayList<>();

            MatchTeamsDTO dto = matchTeams.toDTO();
            MatchResultDTO matchResultDTO = null;
            MatchResult matchResult = matchResultsRepository.findByMatchTeamsIdAndUserUid(matchTeams.getId(), userUid);
            if (matchResult != null)
                matchResultDTO = matchResult.toDTO();
            dto.setMatchResult(matchResultDTO);
            matches.add(dto);

            rounds.put(round, matches);
        });
        return rounds;
    }

    @Override
    public Map<PlayoffStage, List<MatchTeamsDTO>> findAllPlayoffsByTournamentId(long tournamentId, String userUid) {
        Map<PlayoffStage, List<MatchTeamsDTO>> rounds = new TreeMap<>(Comparator.comparingInt(PlayoffStage::getOrder));
        repository.findAllByTournamentIdAndPlayoffStageNotNull(tournamentId)
                .forEach(matchTeams -> {
                    PlayoffStage playoffStage = matchTeams.getPlayoffStage();
                    List<MatchTeamsDTO> matches = rounds.get(playoffStage);

                    if (matches == null) matches = new ArrayList<>();

                    MatchTeamsDTO dto = matchTeams.toDTO();
                    MatchResultDTO matchResultDTO = null;
                    MatchResult matchResult = matchResultsRepository.findByMatchTeamsIdAndUserUid(matchTeams.getId(), userUid);
                    if (matchResult != null)
                        matchResultDTO = matchResult.toDTO();
                    dto.setMatchResult(matchResultDTO);
                    matches.add(dto);

                    rounds.put(playoffStage, matches);
                });
        return rounds;
    }

    @Override
    public MatchTeamsDTO update(MatchTeamsDTO dto) {
        Optional<MatchTeams> optionalMatchTeams = repository.findById(dto.getId());
        if (!optionalMatchTeams.isPresent())
            throw new MatchTeamsNotFoundException("id: " + dto.getId());
        MatchTeams matchTeams = optionalMatchTeams.get();

        if (dto.getScoreAway() != null && dto.getScoreHome() != null) {
            matchTeams.setScoreAway(dto.getScoreAway());
            matchTeams.setScoreHome(dto.getScoreHome());
            matchTeams.setRegisteredTime(Calendar.getInstance());
            if (matchTeams.getPlayoffStage() == null)
                calculatePoints(matchTeams);
            else {
                calculateNextPlayoffGroup(matchTeams);
            }
            matchResultService.calculateBetPoints(matchTeams);
        }
        matchTeams.setScheduledTime(dto.getScheduledTime());
        MatchTeamsDTO matchTeamsDB = repository.save(matchTeams).toDTO();

        //Get the result for the updating user, by default is the creator of the tournament
        MatchResultDTO matchResultDTO = null;
        MatchResult matchResult = matchResultsRepository.findByMatchTeamsIdAndUserUid(
                matchTeams.getId(), matchTeams.getTournament().getUserCreation().getUid());
        if (matchResult != null)
            matchResultDTO = matchResult.toDTO();
        matchTeamsDB.setMatchResult(matchResultDTO);

        return matchTeamsDB;
    }

    private void calculatePoints(MatchTeams matchTeams) {
        int scoreAway = matchTeams.getScoreAway();
        int scoreHome = matchTeams.getScoreHome();
        GroupTeam teamAway = matchTeams.getGroupTeamAway();
        GroupTeam teamHome = matchTeams.getGroupTeamHome();
        teamAway.setGamesPlayed(teamAway.getGamesPlayed() + 1);
        teamHome.setGamesPlayed(teamHome.getGamesPlayed() + 1);
        if (scoreAway > scoreHome) {
            teamAway.setGamesWon(teamAway.getGamesWon() + 1);
            teamAway.setPoints(teamAway.getPoints() + 3);
            teamHome.setGamesLost(teamHome.getGamesLost() + 1);
        } else if (scoreHome > scoreAway) {
            teamHome.setGamesWon(teamHome.getGamesWon() + 1);
            teamHome.setPoints(teamHome.getPoints() + 3);
            teamAway.setGamesLost(teamAway.getGamesLost() + 1);
        } else {
            teamAway.setGamesTied(teamAway.getGamesTied() + 1);
            teamAway.setPoints(teamAway.getPoints() + 1);
            teamHome.setGamesTied(teamHome.getGamesTied() + 1);
            teamHome.setPoints(teamHome.getPoints() + 1);
        }
    }

    private void calculateNextPlayoffGroup(MatchTeams matchTeams) {
        Tournament tournament = matchTeams.getTournament();
        TournamentSettings tournamentSettings = tournamentSettingsRepository.findByTournamentUid(tournament.getUid());
        PlayoffStage playoffStage = matchTeams.getPlayoffStage();
        Group group = matchTeams.getGroup();
        switch (playoffStage) {
            case EIGHTH_FINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'A', 'A');
                if (group.getName() == 'C' || group.getName() == 'D')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'C', 'B');
                if (group.getName() == 'E' || group.getName() == 'F')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'E', 'C');
                if (group.getName() == 'G' || group.getName() == 'H')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'G', 'D');
                break;
            case QUARTER_FINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.SEMIFINALS, 'A', 'A');
                if (group.getName() == 'C' || group.getName() == 'D')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.SEMIFINALS, 'C', 'B');
                break;
            case SEMIFINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignNextPlayoffGroup(matchTeams, tournamentSettings, PlayoffStage.FINALS, 'A', 'A');
                break;
            case FINALS:
                Team team = getWinnerTeam(tournamentSettings, matchTeams, playoffStage);
                if (team != null) {
                    Leaderboard leaderboard = leaderboardRepository.findFirstByLeaderboardKeyTournamentId(tournament.getId());
                    tournament.setUserWinner(leaderboard.getUser());
                    tournament.setTournamentStage(TournamentStage.FINISHED_TOURNAMENT);
                }
                break;

        }
    }

    private void assignNextPlayoffGroup(MatchTeams matchTeams, TournamentSettings tournamentSettings,
                                        PlayoffStage nextPLayoffStage, char initialGroupName, char nextGroupName) {
        PlayoffStage playoffStage = matchTeams.getPlayoffStage();
        Tournament tournament = matchTeams.getTournament();
        Group group = matchTeams.getGroup();

        Group nextGroup = groupRepository.findByTournamentIdAndPlayoffStageAndName(tournament.getId(), nextPLayoffStage, nextGroupName);
        //Validate if there's a winner now
        Team team = getWinnerTeam(tournamentSettings, matchTeams, playoffStage);
        if (team != null) {
            if (group.getName() == initialGroupName)
                groupTeamRepository.save(new GroupTeam(nextGroup, team, 1));
            else
                groupTeamRepository.save(new GroupTeam(nextGroup, team, 0));
            Optional<Group> optionalGroup = groupRepository.findById(nextGroup.getId());
            //if all of the teams of the next group are already there, calculate match(es)
            if (optionalGroup.isPresent() && optionalGroup.get().getGroupTeams().size() == 2)
                calculateNextMatches(tournamentSettings, optionalGroup.get(), nextPLayoffStage);
        }
    }

    private Team getWinnerTeam(TournamentSettings tournamentSettings, MatchTeams matchTeams, PlayoffStage playoffStage) {
        Team team = null;
        switch (playoffStage) {
            case EIGHTH_FINALS:
                team = getTeamByTotalGoals(matchTeams, tournamentSettings.isEightFinalsRoundTrip());
                break;
            case QUARTER_FINALS:
                team = getTeamByTotalGoals(matchTeams, tournamentSettings.isQuarterFinalsRoundTrip());
                break;
            case SEMIFINALS:
                team = getTeamByTotalGoals(matchTeams, tournamentSettings.isSemiFinalsRoundTrip());
                break;
            case FINALS:
                team = getTeamByTotalGoals(matchTeams, tournamentSettings.isFinalRoundTrip());
                break;
        }
        return team;
    }

    private Team getTeamByTotalGoals(MatchTeams matchTeams, boolean isRoundTrip) {
        int scoreHome = 0;
        int scoreAway = 0;
        Group group = matchTeams.getGroup();
        Team team;
        if (isRoundTrip) {
            for (MatchTeams oldMatchTeam: repository.findByGroupId(group.getId())) {
                //If the match has not occurred yet
                if(oldMatchTeam.getRegisteredTime() == null)
                    return null;
                if (matchTeams.getGroupTeamHome().equals(oldMatchTeam.getGroupTeamHome()))
                    scoreHome += oldMatchTeam.getScoreHome();
                if (matchTeams.getGroupTeamHome().equals(oldMatchTeam.getGroupTeamAway()))
                    scoreHome += oldMatchTeam.getScoreAway();
                if (matchTeams.getGroupTeamAway().equals(oldMatchTeam.getGroupTeamHome()))
                    scoreAway += oldMatchTeam.getScoreHome();
                if (matchTeams.getGroupTeamAway().equals(oldMatchTeam.getGroupTeamAway()))
                    scoreAway += oldMatchTeam.getScoreAway();
            }
        } else {
            scoreHome = matchTeams.getScoreHome();
            scoreAway = matchTeams.getScoreAway();
        }
        if (scoreHome > scoreAway)
            team = matchTeams.getGroupTeamHome().getTeam();
        else
            team = matchTeams.getGroupTeamAway().getTeam();
        return team;
    }

    private void calculateNextMatches(TournamentSettings tournamentSettings, Group group, PlayoffStage playoffStage) {
        switch (playoffStage) {
            case EIGHTH_FINALS:
                calculateMatches(group, tournamentSettings.isEightFinalsRoundTrip(), playoffStage);
                break;
            case QUARTER_FINALS:
                calculateMatches(group, tournamentSettings.isQuarterFinalsRoundTrip(), playoffStage);
                break;
            case SEMIFINALS:
                calculateMatches(group, tournamentSettings.isSemiFinalsRoundTrip(), playoffStage);
                break;
            case FINALS:
                calculateMatches(group, tournamentSettings.isFinalRoundTrip(), playoffStage);
                break;
        }
    }

    @Override
    public void calculateMatches(Group group, boolean roundTrip, PlayoffStage playoffStage) {
        Tournament tournament = group.getTournament();
        List<GroupTeam> groupTeams = group.getGroupTeams();
        int size = groupTeams.size();
        boolean oddTeams = groupTeams.size() % 2 == 1;
        if (oddTeams) {
            groupTeams.add(new GroupTeam());
            size++;
        }
        Stack<GroupTeam> awayStack = new Stack<>();
        awayStack.addAll(groupTeams.subList(0, size / 2));
        Stack<GroupTeam> homeStack = new Stack<>();
        homeStack.addAll(groupTeams.subList(size / 2, size));
        List<MatchTeams> matches = new ArrayList<>();
        for (int round = 1; round < size; round++) {
            for (int i = 0; i < size / 2; i++) {
                if(awayStack.get(i).getId() > 0 && homeStack.get(i).getId() > 0) {
                    matches.add( createMatch(tournament, group, awayStack.get(i), homeStack.get(i), round, playoffStage));
                    if (roundTrip)
                        matches.add( createMatch(tournament, group, homeStack.get(i), awayStack.get(i), round + size -1, playoffStage));
                }
            }
            if (size > 2) {
                homeStack.push(awayStack.pop());
                awayStack.add(1, homeStack.remove(0));
            }
        }
        if (oddTeams) group.getGroupTeams().remove(size - 1);
        repository.saveAll(matches);
    }

    private MatchTeams createMatch(Tournament tournament, Group group, GroupTeam away, GroupTeam home, int round, PlayoffStage playoffStage) {
        MatchTeams matchTeams = new MatchTeams();
        matchTeams.setTournament(tournament);
        matchTeams.setGroup(group);
        matchTeams.setGroupTeamAway(away);
        matchTeams.setGroupTeamHome(home);
        matchTeams.setRound(round);
        matchTeams.setPlayoffStage(playoffStage);

        return matchTeams;
    }

}
