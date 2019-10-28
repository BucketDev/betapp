package com.bucketdev.betapp.domain.user;

import com.bucketdev.betapp.dto.user.SubUserFollowerDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class SubUserFollower {

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
    private List<User> following;

    @ManyToMany
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_followed_id"),
            inverseJoinColumns = @JoinColumn(name = "user_following_id")
    )
    private List<User> followers;

    public SubUserFollowerDTO toDTO() {
        SubUserFollowerDTO dto = new SubUserFollowerDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setDisplayName(displayName);
        dto.setDescription(description);
        dto.setPhotoUrl(photoUrl);
        dto.setFollowingCount(following.size());
        dto.setFollowersCount(followers.size());

        return dto;
    }

}
