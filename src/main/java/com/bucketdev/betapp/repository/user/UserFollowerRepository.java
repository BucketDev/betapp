package com.bucketdev.betapp.repository.user;

import com.bucketdev.betapp.domain.user.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {

    UserFollower findByUid(String uid);

}
