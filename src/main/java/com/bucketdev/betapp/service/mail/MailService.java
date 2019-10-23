package com.bucketdev.betapp.service.mail;

import com.bucketdev.betapp.dto.user.UserDTO;
import com.bucketdev.betapp.type.MailType;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface MailService {

    void sendEmail(MailType type, List<UserDTO> users);

}
