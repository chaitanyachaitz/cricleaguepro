package com.cricleaguepro.apiserver.league;

import com.cricleaguepro.apiserver.websocket.LeagueWebSocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LeagueEventsConsumer {

    private static final Logger log = LoggerFactory.getLogger(LeagueEventsConsumer.class);
    private static final String TOPIC = "league-events";

    private final LeagueWebSocketController webSocketController;

    public LeagueEventsConsumer(LeagueWebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    @KafkaListener(topics = TOPIC, groupId = "cricleaguepro-api")
    public void consumeLeagueEvent(String message) {
        log.info("=== CONSUMED LEAGUE EVENT ===");
        log.info("Raw message: {}", message);
        // Forward to WebSocket clients
        webSocketController.broadcastLeagueEvent(message);
        log.info(">>> BROADCAST TO WEBSOCKET CLIENTS <<<");
        log.info("==============================");
    }
}
