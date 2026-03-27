package com.cricleaguepro.apiserver.player;

import com.cricleaguepro.apiserver.service.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerRepository playerRepository;
    private final CsvService csvService;

    public PlayerController(PlayerRepository playerRepository, CsvService csvService) {
        this.playerRepository = playerRepository;
        this.csvService = csvService;
    }

    @GetMapping
    public List<Player> all() {
        return playerRepository.findAll();
    }

    @PostMapping
    public Player create(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @GetMapping("/team/{teamId}")
    public List<Player> byTeam(@PathVariable Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> bulkCreate(@RequestParam("teamId") Long teamId,
                                             @RequestParam("file") MultipartFile csvFile) {
        try {
            List<Player> players = csvService.parsePlayers(csvFile, teamId);
            playerRepository.saveAll(players);
            return ResponseEntity.ok("Imported " + players.size() + " players for team " + teamId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Import failed: " + e.getMessage());
        }
    }

    @PostMapping("/bulk-json")
    public ResponseEntity<String> bulkJsonCreate(@RequestBody List<Player> players) {
        List<Player> saved = playerRepository.saveAll(players);
        return ResponseEntity.ok("Imported " + saved.size() + " players");
    }
}
