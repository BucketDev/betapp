package com.bucketdev.betapp.domain;

import com.bucketdev.betapp.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String uid;

    @Column
    @NotNull
    private String email;

    @Column
    private String displayName;

    @Column
    private String photoUrl;

    @Column
    private String providerId;

    public UserDTO toDTO() {
        UserDTO dto = new UserDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setEmail(email);
        dto.setDisplayName(displayName);
        dto.setPhotoUrl(photoUrl);
        dto.setProviderId(providerId);

        return dto;
    }

    public void setValuesFromDTO(UserDTO dto) {
        uid = dto.getUid();
        email = dto.getEmail();
        displayName = dto.getDisplayName();
        photoUrl = dto.getPhotoUrl();
        providerId = dto.getProviderId();
    }

}
