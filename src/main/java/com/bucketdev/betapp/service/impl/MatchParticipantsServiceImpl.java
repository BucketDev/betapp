package com.bucketdev.betapp.service.impl;

import com.bucketdev.betapp.domain.*;
import com.bucketdev.betapp.dto.MatchParticipantsDTO;
import com.bucketdev.betapp.exception.groupParticipant.GroupParticipantsNotSufficient;
import com.bucketdev.betapp.exception.matchParticipants.MatchParticipantsNotFoundException;
import com.bucketdev.betapp.repository.GroupParticipantRepository;
import com.bucketdev.betapp.repository.GroupRepository;
import com.bucketdev.betapp.repository.MatchParticipantsRepository;
import com.bucketdev.betapp.repository.TournamentSettingsRepository;
import com.bucketdev.betapp.service.MatchParticipantsService;
import com.bucketdev.betapp.service.TournamentService;
import com.bucketdev.betapp.type.PlayoffStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TournamentSettingsRepository tournamentSettingsRepository;

    @Autowired
    private MatchParticipantsRepository matchParticipantsRepository;

    @Autowired
    private TournamentService tournamentService;

    @Override
    public List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId) {
        return repository.findAllByTournamentIdAndPlayoffStageIsNull(tournamentId).stream()
                .map(MatchParticipants::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MatchParticipantsDTO> findAllPlayoffsByTournamentId(long tournamentId) {
        return repository.findAllByTournamentIdAndPlayoffStageNotNull(tournamentId).stream()
                .map(MatchParticipants::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MatchParticipantsDTO update(MatchParticipantsDTO dto) {
        Optional<MatchParticipants> optionalMatchParticipants = repository.findById(dto.getId());
        if (!optionalMatchParticipants.isPresent())
            throw new MatchParticipantsNotFoundException("id: " + dto.getId());
        MatchParticipants matchParticipants = optionalMatchParticipants.get();

        if (dto.getScoreAway() != null && dto.getScoreHome() != null) {
            matchParticipants.setScoreAway(dto.getScoreAway());
            matchParticipants.setScoreHome(dto.getScoreHome());
            matchParticipants.setRegisteredTime(Calendar.getInstance());
            if (matchParticipants.getPlayoffStage() == null)
                calculatePoints(matchParticipants);
        }
        matchParticipants.setScheduledTime(dto.getScheduledTime());

        MatchParticipantsDTO savedDOT = repository.save(matchParticipants).toDTO();

        //if it is a playoff match
        if (matchParticipants.getPlayoffStage() != null)
            try {
                calculateNextGroup(matchParticipants);
            } catch (GroupParticipantsNotSufficient gpns) {
                System.out.println(gpns.getMessage());
            }

        return savedDOT;
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

    private void calculateNextGroup(MatchParticipants matchParticipants) {
        Tournament tournament = matchParticipants.getTournament();
        TournamentSettings tournamentSettings = tournamentSettingsRepository.findByTournamentUid(tournament.getUid());
        PlayoffStage playoffStage = matchParticipants.getPlayoffStage();
        Group group = matchParticipants.getGroup();
        switch (playoffStage) {
            case EIGHTH_FINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'A', 'A');
                if (group.getName() == 'C' || group.getName() == 'D')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'C', 'B');
                if (group.getName() == 'E' || group.getName() == 'F')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'E', 'C');
                if (group.getName() == 'G' || group.getName() == 'H')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'G', 'D');
                break;
            case QUARTER_FINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.SEMIFINALS, 'A', 'A');
                if (group.getName() == 'C' || group.getName() == 'D')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.SEMIFINALS, 'C', 'B');
                break;
            case SEMIFINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignGroupParticipant(matchParticipants, tournamentSettings, PlayoffStage.FINALS, 'A', 'A');
                break;
            case FINALS:
                User user = getWinnerUser(tournamentSettings, matchParticipants, playoffStage);
                if (user != null) {
                    tournament.setUserWinner(user);
                }
                break;

        }
    }

    private void assignGroupParticipant(MatchParticipants matchParticipants, TournamentSettings tournamentSettings,
                                        PlayoffStage nextPLayoffStage, char initialGroupName, char nextGroupName) {
        PlayoffStage playoffStage = matchParticipants.getPlayoffStage();
        Tournament tournament = matchParticipants.getTournament();
        Group group = matchParticipants.getGroup();

        Group nextGroup = groupRepository.findByTournamentIdAndPlayoffStageAndName(tournament.getId(), nextPLayoffStage, nextGroupName);
        User user = getWinnerUser(tournamentSettings, matchParticipants, playoffStage);
        if (user != null) {
            if (group.getName() == initialGroupName) {
                GroupParticipant groupParticipant = getNewParticipant(nextGroup, user, 1);
                groupParticipantRepository.save(groupParticipant);
            } else {
                GroupParticipant groupParticipant = getNewParticipant(nextGroup, user, 0);
                groupParticipantRepository.save(groupParticipant);
            }
        }
        Optional<Group> optionalGroup = groupRepository.findById(nextGroup.getId());
        //if all of the participants of the next group are already there, calculate match(es)
        if (optionalGroup.isPresent() && optionalGroup.get().getGroupParticipants().size() == 2) {
            calculateNextMatches(tournamentSettings, optionalGroup.get(), nextPLayoffStage);
        }
    }

    private void calculateNextMatches(TournamentSettings tournamentSettings, Group group, PlayoffStage playoffStage) {
        switch (playoffStage) {
            case EIGHTH_FINALS:
                if (!tournamentSettings.isEightFinalsRoundTrip())
                    tournamentService.oneTripFinalMatchesPerGroup(group, playoffStage);
                break;
            case QUARTER_FINALS:
                if (!tournamentSettings.isQuarterFinalsRoundTrip())
                    tournamentService.oneTripFinalMatchesPerGroup(group, playoffStage);
                break;
            case SEMIFINALS:
                if (!tournamentSettings.isSemiFinalsRoundTrip())
                    tournamentService.oneTripFinalMatchesPerGroup(group, playoffStage);
                break;
            case FINALS:
                if (!tournamentSettings.isFinalRoundTrip())
                    tournamentService.oneTripFinalMatchesPerGroup(group, playoffStage);
                break;
        }
    }

    private User getWinnerUser(TournamentSettings tournamentSettings, MatchParticipants matchParticipants, PlayoffStage playoffStage) {
        User user = null;
        switch (playoffStage) {
            case EIGHTH_FINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isEightFinalsRoundTrip());
                break;
            case QUARTER_FINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isQuarterFinalsRoundTrip());
                break;
            case SEMIFINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isSemiFinalsRoundTrip());
                break;
            case FINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isFinalRoundTrip());
                break;
        }
        return user;
    }

    private User getUserByTotalGoals(MatchParticipants matchParticipants, boolean isRoundTrip) {
        int scoreHome = 0;
        int scoreAway = 0;
        Group group = matchParticipants.getGroup();
        User user;
        if (isRoundTrip) {
            for (MatchParticipants oldMatchParticipant: matchParticipantsRepository.findByGroupId(group.getId())) {
                if(oldMatchParticipant.getRegisteredTime() == null)
                    return null;
                if (matchParticipants.getGroupParticipantHome().equals(oldMatchParticipant.getGroupParticipantHome()))
                    scoreHome += oldMatchParticipant.getScoreHome();
                if (matchParticipants.getGroupParticipantHome().equals(oldMatchParticipant.getGroupParticipantAway()))
                    scoreHome += oldMatchParticipant.getScoreAway();
                if (matchParticipants.getGroupParticipantAway().equals(oldMatchParticipant.getGroupParticipantHome()))
                    scoreAway += oldMatchParticipant.getScoreHome();
                if (matchParticipants.getGroupParticipantAway().equals(oldMatchParticipant.getGroupParticipantAway()))
                    scoreAway += oldMatchParticipant.getScoreAway();
            }
        } else {
            scoreHome = matchParticipants.getScoreHome();
            scoreAway = matchParticipants.getScoreAway();
        }
        if (scoreHome > scoreAway)
            user = matchParticipants.getGroupParticipantHome().getUser();
        else
            user = matchParticipants.getGroupParticipantAway().getUser();
        return user;
    }

    private GroupParticipant getNewParticipant(Group group, User participant, int points) {
        GroupParticipant groupParticipant = new GroupParticipant();
        groupParticipant.setGroup(group);
        groupParticipant.setTournament(group.getTournament());
        groupParticipant.setUser(participant);
        groupParticipant.setGamesPlayed(0);
        groupParticipant.setGamesWon(0);
        groupParticipant.setGamesTied(0);
        groupParticipant.setGamesLost(0);
        groupParticipant.setPoints(points);

        return groupParticipant;
    }

}
