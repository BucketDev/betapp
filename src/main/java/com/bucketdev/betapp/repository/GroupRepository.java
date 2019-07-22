package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    void deleteByTournamentUid(String uid);

    List<Group> findAllByTournamentUidOrderByName(String uid);

}
