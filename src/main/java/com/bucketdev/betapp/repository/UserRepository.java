package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUid(String uid);

}
