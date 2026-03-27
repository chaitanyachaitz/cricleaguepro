package com.cricleaguepro.apiserver.service;

import com.cricleaguepro.apiserver.player.Player;
import com.cricleaguepro.apiserver.team.Team;
import com.cricleaguepro.apiserver.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    private final TeamRepository teamRepository;

    public CsvService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Player> parsePlayers(MultipartFile csvFile, Long teamId) throws IOException {
        List<Player> players = new ArrayList<>();
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found: " + teamId));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            br.readLine(); // Skip header: "CC Player Id","Player Name","..."

            String line;
            while ((line = br.readLine()) != null) {
                // Handle quoted CSV properly
                String cleanLine = line.replaceAll("\"", "");
                String[] columns = cleanLine.split(",");

                if (columns.length < 2) continue;

                // Player Name is ALWAYS column index 1
                String fullName = columns[1].trim();
                if (fullName.isEmpty() || fullName.equals("Player Name")) continue;

                String jersey = columns.length > 4 ? columns[4].trim() : "0";
                String role = getRoleFromJersey(jersey);

                Player player = new Player(
                        fullName,
                        "Right Hand Bat",
                        getBowlingStyle(role),
                        role,
                        team
                );

                players.add(player);
                System.out.println("✅ Added: " + fullName + " | Jersey: " + jersey + " | Role: " + role);
            }
        }

        return players;
    }

    private String getRoleFromJersey(String jersey) {
        return switch (jersey) {
            case "0" -> "Batsman";
            case "44", "99", "21", "28", "14" -> "All-rounder";
            case "7" -> "Batsman";
            default -> "Bowler";
        };
    }

    private String getBowlingStyle(String role) {
        return switch (role) {
            case "Spinner" -> "Right Arm Offbreak";
            case "Bowler" -> "Right Arm Fast";
            default -> "Right Arm Medium";
        };
    }
}
