package com.bucketdev.betapp.dto.leaderboard;

import com.bucketdev.betapp.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Getter
@Setter
public class LeaderboardDTO implements Serializable {

    private UserDTO user;
    private int points;

}
