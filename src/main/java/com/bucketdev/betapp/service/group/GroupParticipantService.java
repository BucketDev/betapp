package com.bucketdev.betapp.service.group;

import com.bucketdev.betapp.dto.group.GroupParticipantDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface GroupParticipantService {

    GroupParticipantDTO save(GroupParticipantDTO groupParticipantDTO);

    List<GroupParticipantDTO> findByTournamentId(long tournamentId);

}
