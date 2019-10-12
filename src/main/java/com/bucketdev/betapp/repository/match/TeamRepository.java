package com.bucketdev.betapp.repository.match;

import com.bucketdev.betapp.domain.match.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
