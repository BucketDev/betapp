package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.user.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    UserNotification findByUid(String uid);

}
