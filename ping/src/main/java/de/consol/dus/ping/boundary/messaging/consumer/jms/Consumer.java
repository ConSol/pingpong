package de.consol.dus.ping.boundary.messaging.consumer.jms;

import de.consol.dus.ping.boundary.http.client.ReportDto;
import de.consol.dus.ping.boundary.http.client.Reporter;
import de.consol.dus.ping.boundary.messaging.producer.kafka.Producer;
import de.consol.dus.ping.game.Game;
import de.consol.dus.ping.game.GameService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class Consumer {
  protected static final String MESSAGES_TOPIC = "messages";

  private final GameService gameService;
  private final Producer producer;
  private final Reporter reporter;

  @JmsListener(
      destination = MESSAGES_TOPIC,
      containerFactory = "jmsListenerFactory",
      subscription = "ping")
  public void consume(
      Game game,
      @Header("CREATION_TIME") long creationTime) {
    final Instant timestamp = Instant.ofEpochMilli(creationTime);
    log.info("received game [{}] from kafka-topic [{}] at {}", game, MESSAGES_TOPIC, timestamp);
    final Instant now = Instant.now();
    final long pingDelta = now.toEpochMilli() - timestamp.toEpochMilli();
    final Game nextRound = gameService.incrementGamesPlayedByOne(
        gameService.increaseTimePingBy(game, pingDelta));
    if (nextRound.getRoundsPlayed() >= nextRound.getRoundsToPlay()) {
      reporter.postReport(ReportDto.builder()
          .gameId(nextRound.getId().toString())
          .timePing(nextRound.getTimePing())
          .timePong(nextRound.getTimePong())
          .roundsPlayed(nextRound.getRoundsPlayed())
          .build());
    } else {
      producer.send(nextRound);
    }
  }
}
