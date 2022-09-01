package de.consol.dus.ping.boundary.messaging.producer.kafka;

import de.consol.dus.ping.game.Game;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class Producer {
  private static final String MESSAGES_TOPIC = "messages-kafka";

  private final KafkaTemplate<String, Game> kafkaTemplate;

  public void send(Game game) {
    final String id = UUID.randomUUID().toString();
    log.info("Sending game [{}] with to kafka-topic [{}]", game, MESSAGES_TOPIC);
    kafkaTemplate.send(MessageBuilder.withPayload(game)
        .setHeaderIfAbsent("CREATION_TIME", Instant.now().toEpochMilli())
        .setHeaderIfAbsent(KafkaHeaders.TOPIC, MESSAGES_TOPIC)
        .setHeaderIfAbsent(KafkaHeaders.CORRELATION_ID, id)
        .build());
  }
}
