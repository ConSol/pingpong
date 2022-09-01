package de.consol.dus.ping.boundary.messaging.producer.kafka;

import de.consol.dus.ping.game.Game;

import java.time.Instant;
import java.util.UUID;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class Producer {
    private final String destination;

    private final KafkaTemplate<String, Game> kafkaTemplate;

    public Producer(
            @Value("${messaging.kafka.destination}") String destination,
            KafkaTemplate<String, Game> kafkaTemplate) {
        this.destination = destination;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Game game) {
        final String id = UUID.randomUUID().toString();
        log.info("Sending game [{}] with to kafka-topic [{}]", game, destination);
        kafkaTemplate.send(MessageBuilder.withPayload(game)
                .setHeaderIfAbsent("CREATION_TIME", Instant.now().toEpochMilli())
                .setHeaderIfAbsent(KafkaHeaders.TOPIC, destination)
                .setHeaderIfAbsent(KafkaHeaders.CORRELATION_ID, id)
                .build());
    }
}
