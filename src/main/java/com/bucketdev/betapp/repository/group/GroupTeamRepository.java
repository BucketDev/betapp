package com.bucketdev.betapp.repository.group;

import com.bucketdev.betapp.domain.group.GroupTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTeamRepository extends JpaRepository<GroupTeam, Long> {
}
