package com.cricleaguepro.apiserver.team;

import com.cricleaguepro.apiserver.league.League;
import com.cricleaguepro.apiserver.league.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamRepository teamRepository;
    @Autowired
    private final LeagueRepository leagueRepository;

    public TeamController(TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    @GetMapping
    public List<Team> all() {
        return teamRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Team> create(@RequestBody TeamCreateRequest request) {
        League league = leagueRepository.findById(request.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found"));
        Team team = new Team(request.getName(), request.getShortName(),
                request.getLogoUrl(), league);
        return ResponseEntity.ok(teamRepository.save(team));
    }


    @GetMapping("/league/{leagueId}")
    public List<Team> byLeague(@PathVariable Long leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }
}
