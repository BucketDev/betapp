package com.bucketdev.betapp.repository.group;

import com.bucketdev.betapp.domain.group.Group;
import com.bucketdev.betapp.type.PlayoffStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    void deleteByTournamentUid(String uid);

    @Query("SELECT g FROM Group g WHERE g.tournament.uid = :uid AND g.playoffStage IS NULL ORDER BY g.name")
    List<Group> findAllByTournamentUid(@Param("uid") String uid);

    @Query("SELECT g FROM Group g WHERE g.tournament.uid = :uid AND g.playoffStage IS NOT NULL ORDER BY g.name")
    List<Group> findAllPlayoffsByTournamentUid(@Param("uid") String uid);

    @Query("SELECT g FROM Group g WHERE g.tournament.uid = :uid AND g.playoffStage = :playoffStage ORDER BY g.name")
    List<Group> findAllPlayoffsByTournamentUidAndPlayoffStage(@Param("uid") String uid,
                                                              @Param("playoffStage") PlayoffStage playoffStage);

    Group findByTournamentIdAndPlayoffStageAndName(long tournamentId, PlayoffStage playoffStage, char name);

}
