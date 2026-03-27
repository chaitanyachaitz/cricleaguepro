package com.cricleaguepro.apiserver.player;

import com.cricleaguepro.apiserver.service.CsvService;
import com.cricleaguepro.apiserver.team.Team;
import com.cricleaguepro.apiserver.team.TeamRepository;
import com.cricleaguepro.apiserver.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CsvService csvService;

    // Upload CSV to team
    @PostMapping("/upload/{teamId}")
    public ResponseEntity<String> uploadPlayers(
            @PathVariable Long teamId,
            @RequestParam("file") MultipartFile file) {

        try {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new RuntimeException("Team not found"));

            List<Player> players = csvService.csvToPlayers(file, team);
            playerRepository.saveAll(players);

            return ResponseEntity.ok("Uploaded " + players.size() + " players to " + team.getName());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get all players for team
    @GetMapping("/team/{teamId}")
    public List<Player> getTeamPlayers(@PathVariable Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    // Get captains/vice captains for team
    @GetMapping("/team/{teamId}/leaders")
    public List<Player> getTeamLeaders(@PathVariable Long teamId) {
        return playerRepository.findCaptainsAndViceCaptainsByTeamId(teamId);
    }
}
