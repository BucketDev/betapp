package com.bucketdev.betapp.service.group;

import com.bucketdev.betapp.dto.group.GroupParticipantDTO;
import com.bucketdev.betapp.dto.user.UserDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface GroupParticipantService {

    GroupParticipantDTO save(GroupParticipantDTO groupParticipantDTO);
    List<GroupParticipantDTO> saveByGroupId(long groupId, List<UserDTO> users);
    List<GroupParticipantDTO> findByTournamentId(long tournamentId);

}
