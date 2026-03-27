package com.cricleaguepro.apiserver.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(Long teamId);

    @Query("SELECT p FROM Player p WHERE p.team.id = :teamId AND (p.isCaptain = true OR p.isViceCaptain = true)")
    List<Player> findCaptainsAndViceCaptainsByTeamId(@Param("teamId") Long teamId);
}
