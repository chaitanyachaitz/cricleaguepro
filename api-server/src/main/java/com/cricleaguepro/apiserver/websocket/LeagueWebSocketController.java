package com.cricleaguepro.apiserver.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LeagueWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public LeagueWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastLeagueEvent(String event) {
        messagingTemplate.convertAndSend("/topic/league-events", event);
    }
}
