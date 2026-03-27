package com.cricleaguepro.apiserver.match;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping
    public List<Match> all() {
        return matchRepository.findAll();
    }

    @PostMapping
    public Match create(@RequestBody Match match) {
        return matchRepository.save(match);
    }

    @GetMapping("/team/{teamId}")
    public List<Match> byTeam(@PathVariable Long teamId) {
        return matchRepository.findByTeam1IdOrTeam2IdOrderByScheduledStart(teamId, teamId);
    }
}
