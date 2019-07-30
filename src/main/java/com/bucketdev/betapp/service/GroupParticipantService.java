package com.bucketdev.betapp.service;

import com.bucketdev.betapp.dto.GroupParticipantDTO;
import com.bucketdev.betapp.dto.UserDTO;

/**
 * @author rodrigo.loyola
 */
public interface GroupParticipantService {

    UserDTO save(GroupParticipantDTO groupParticipantDTO);

}
