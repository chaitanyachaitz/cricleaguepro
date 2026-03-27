package com.cricleaguepro.apiserver.match;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTeam1IdOrTeam2IdOrderByScheduledStart(Long team1Id, Long team2Id);
}
