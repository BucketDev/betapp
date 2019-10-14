package com.bucketdev.betapp.domain.user;

import com.bucketdev.betapp.dto.user.UserFollowerCountDTO;
import com.bucketdev.betapp.dto.user.UserFollowerDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String uid;

    @Column
    private String displayName;

    @Column
    private String description;

    @Column
    private String photoUrl;

    @ManyToMany
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_following_id"),
            inverseJoinColumns = @JoinColumn(name = "user_followed_id")
    )
    private Set<User> following;

    @ManyToMany
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_followed_id"),
            inverseJoinColumns = @JoinColumn(name = "user_following_id")
    )
    private Set<User> followers;

    public UserFollowerDTO toDTO() {
        UserFollowerDTO dto = new UserFollowerDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setDisplayName(displayName);
        dto.setDescription(description);
        dto.setPhotoUrl(photoUrl);
        dto.setFollowing(following.stream().map(User::toDTO).collect(Collectors.toSet()));
        dto.setFollowers(followers.stream().map(User::toDTO).collect(Collectors.toSet()));

        return dto;
    }

    public UserFollowerCountDTO toCountDTO() {
        UserFollowerCountDTO dto = new UserFollowerCountDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setDisplayName(displayName);
        dto.setPhotoUrl(photoUrl);
        dto.setFollowing(following.size());
        dto.setFollowers(followers.size());

        return dto;
    }

}
