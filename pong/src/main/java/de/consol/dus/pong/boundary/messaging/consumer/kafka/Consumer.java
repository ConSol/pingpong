package de.consol.dus.pong.boundary.messaging.consumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.consol.dus.pong.boundary.messaging.producer.jms.Producer;
import de.consol.dus.pong.game.Game;
import de.consol.dus.pong.game.GameService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class Consumer {
  protected static final String MESSAGES_TOPIC = "messages";

  private final GameService gameService;
  private final Producer producer;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = MESSAGES_TOPIC, groupId = "pong")
  public void consume(
      ConsumerRecord<String, Game> record,
      @Header("CREATION_TIME") long creationTime) {
    final Instant now = Instant.now();
    final Instant timestamp = Instant.ofEpochMilli(creationTime);
    final Game game = record.value();
    log.info("received game [{}] from kafka-topic [{}] at {}", game, MESSAGES_TOPIC, timestamp);
    final long pongDelta = now.toEpochMilli() - timestamp.toEpochMilli();
    final Game nextRound = gameService.incrementGamesPlayedByOne(
        gameService.increaseTimePongBy(game, pongDelta));
    if (nextRound.getRoundsPlayed() >= nextRound.getRoundsToPlay()) {
      log.info("Last round, game is over. Result: [{}]", nextRound);
    } else {
      producer.send(nextRound);
    }
  }
}
