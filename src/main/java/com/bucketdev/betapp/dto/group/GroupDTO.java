package com.bucketdev.betapp.dto.group;

import com.bucketdev.betapp.type.PlayoffStage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class GroupDTO implements Serializable {

    private long id;
    private char name;
    private long tournamentId;
    private PlayoffStage playoffStage;
    private List<GroupParticipantDTO> groupParticipants;
    private List<GroupTeamDTO> groupTeams;

}
