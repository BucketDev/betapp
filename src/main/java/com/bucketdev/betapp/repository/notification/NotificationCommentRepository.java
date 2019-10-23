package com.bucketdev.betapp.repository.notification;

import com.bucketdev.betapp.domain.notification.NotificationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface NotificationCommentRepository extends JpaRepository<NotificationComment, Long> {

    List<NotificationComment> findAllByNotificationIdOrderByCreationDate(long notificationId);

}
