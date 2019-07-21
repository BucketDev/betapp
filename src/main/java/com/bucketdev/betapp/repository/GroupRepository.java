package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
