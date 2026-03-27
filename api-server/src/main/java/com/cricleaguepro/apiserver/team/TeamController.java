package com.cricleaguepro.apiserver.team;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<Team> all() {
        return teamRepository.findAll();
    }

    @PostMapping
    public Team create(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    @GetMapping("/league/{leagueId}")
    public List<Team> byLeague(@PathVariable Long leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }
}
