package com.cricleaguepro.apiserver.league;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LeagueEventsProducer {

    private static final String TOPIC = "league-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LeagueEventsProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLeagueCreatedEvent(League league) {
        String payload = String.format("{\"id\":%d,\"name\":\"%s\"}", league.getId(), league.getName());
        kafkaTemplate.send(TOPIC, String.valueOf(league.getId()), payload);
    }
}
