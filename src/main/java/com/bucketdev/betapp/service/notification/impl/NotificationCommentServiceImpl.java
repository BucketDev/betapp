package com.bucketdev.betapp.service.notification.impl;

import com.bucketdev.betapp.domain.notification.Notification;
import com.bucketdev.betapp.domain.notification.NotificationComment;
import com.bucketdev.betapp.domain.user.User;
import com.bucketdev.betapp.dto.notification.NotificationCommentDTO;
import com.bucketdev.betapp.exception.notification.NotificationNotFoundException;
import com.bucketdev.betapp.exception.user.UserNotFoundException;
import com.bucketdev.betapp.repository.notification.NotificationCommentRepository;
import com.bucketdev.betapp.repository.notification.NotificationRepository;
import com.bucketdev.betapp.repository.user.UserRepository;
import com.bucketdev.betapp.service.notification.NotificationCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class NotificationCommentServiceImpl implements NotificationCommentService {

    @Autowired
    private NotificationCommentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationCommentDTO> findAllByNotificationId(long commentId) {
        return repository.findAllByNotificationIdOrderByCreationDate(commentId)
                .stream().map(NotificationComment::toDTO).collect(Collectors.toList());
    }

    @Override
    public NotificationCommentDTO save(NotificationCommentDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUser().getId());
        if (!optionalUser.isPresent())
            throw new UserNotFoundException("id:", String.valueOf(dto.getUser().getId()));
        User user = optionalUser.get();
        Optional<Notification> optionalNotification = notificationRepository.findById(dto.getNotificationId());
        if (!optionalNotification.isPresent())
            throw new NotificationNotFoundException("id:", String.valueOf(dto.getNotificationId()));
        Notification notification = optionalNotification.get();

        NotificationComment comment = new NotificationComment();

        comment.setUser(user);
        comment.setNotification(notification);
        comment.setComment(dto.getComment());

        return repository.save(comment).toDTO();
    }
}
