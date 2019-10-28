package com.bucketdev.betapp.repository.user;

import com.bucketdev.betapp.domain.user.SubUserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface SubUserFollowerRepository extends JpaRepository<SubUserFollower, Long> {

    SubUserFollower findByUid(String uid);

}
