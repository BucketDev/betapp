package com.bucketdev.betapp.repository.notification;

import com.bucketdev.betapp.domain.notification.NotificationLikes;
import com.bucketdev.betapp.domain.notification.NotificationLikesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface NotificationLikesRepository extends JpaRepository<NotificationLikes, NotificationLikesKey> {

    @Query("SELECT nl FROM NotificationLikes nl WHERE nl.notificationLikesKey.notificationId = :notificationId")
    Set<NotificationLikes> findAllByNotificationId(@Param("notificationId") long notificationId);

    @Query("SELECT nl FROM NotificationLikes nl WHERE nl.notificationLikesKey.notificationId = :notificationId " +
            "AND nl.notificationLikesKey.userId = :userId")
    NotificationLikes findByNotificationIdAndUserId(@Param("notificationId") long notificationId, @Param("userId") long userId);

}
