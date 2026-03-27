package com.cricleaguepro.apiserver.league;

import com.cricleaguepro.apiserver.websocket.LeagueWebSocketController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {
    private final LeagueRepository leagueRepository;
    private final LeagueEventsProducer eventsProducer;
    private final LeagueWebSocketController webSocketController;

    public LeagueController(LeagueRepository leagueRepository, LeagueEventsProducer eventsProducer, LeagueWebSocketController webSocketController) {
        this.leagueRepository = leagueRepository;
        this.eventsProducer = eventsProducer;
        this.webSocketController = webSocketController;
    }

    @GetMapping
    public List<League> all() {
        return leagueRepository.findAll();
    }

    @PostMapping
    public League create(@RequestBody League league) {
        League saved = leagueRepository.save(league);
        eventsProducer.sendLeagueCreatedEvent(saved);
        return saved;
    }

    @GetMapping("/test-websocket")
    public String testWebSocket() {
        String testMessage = "{\"id\":999,\"name\":\"WEBSOCKET TEST\"}";
        webSocketController.broadcastLeagueEvent(testMessage);  // Add webSocketController field
        return "Broadcast sent: " + testMessage;
    }
}

