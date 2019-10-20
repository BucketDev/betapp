package com.bucketdev.betapp.repository.notification;

import com.bucketdev.betapp.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
