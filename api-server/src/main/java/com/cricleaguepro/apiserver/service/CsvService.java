package com.cricleaguepro.apiserver.service;

import com.cricleaguepro.apiserver.player.Player;
import com.cricleaguepro.apiserver.team.Team;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    public List<Player> csvToPlayers(MultipartFile file, Team team) throws IOException, CsvValidationException {
        List<Player> players = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {

            // Skip header
            csvReader.readNext();

            String[] record;
            while ((record = csvReader.readNext()) != null) {
                if (record.length >= 4) {
                    String name = record[0].trim();
                    String role = record.length > 1 ? record[1].trim() : "BAT";
                    Boolean isCaptain = record.length > 2 && "Captain".equals(record[2].trim());
                    Boolean isViceCaptain = record.length > 3 && "Vice Captain".equals(record[3].trim());

                    Player player = new Player(
                            name,
                            role,
                            isCaptain,
                            isViceCaptain,
                            team
                    );
                    players.add(player);
                }
            }
        }

        return players;
    }
}
