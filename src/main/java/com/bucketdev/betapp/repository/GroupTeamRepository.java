package com.bucketdev.betapp.repository;

import com.bucketdev.betapp.domain.GroupTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTeamRepository extends JpaRepository<GroupTeam, Long> {
}
