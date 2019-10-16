package com.bucketdev.betapp.repository.notification;

import com.bucketdev.betapp.domain.notification.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface NotificationUserRepository extends JpaRepository<NotificationUser, Long> {

    Set<NotificationUser> findAllByUserFollowingId(long id);

}
