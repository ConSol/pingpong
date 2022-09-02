package de.consol.dus.pong.boundary.messaging.consumer.kafka;

import de.consol.dus.pong.boundary.http.client.ReportDto;
import de.consol.dus.pong.boundary.http.client.Reporter;
import de.consol.dus.pong.boundary.messaging.producer.jms.Producer;
import de.consol.dus.pong.game.Game;
import de.consol.dus.pong.game.GameService;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class Consumer {
  private final String destination;

  private final GameService gameService;
  private final Producer producer;
  private final Reporter reporter;

  public Consumer(
          @Value("${messaging.kafka.destination}") String destination,
          GameService gameService,
          Producer producer,
          Reporter reporter) {
    this.destination = destination;
    this.gameService = gameService;
    this.producer = producer;
    this.reporter = reporter;
  }

  @KafkaListener(
          topics = "${messaging.kafka.destination}",
          groupId = "${messaging.kafka.group-id}")
  public void consume(
      ConsumerRecord<String, Game> consumerRecord,
      @Header("CREATION_TIME") long creationTime) {
    final Instant now = Instant.now();
    final Instant timestamp = Instant.ofEpochMilli(creationTime);
    final Game game = consumerRecord.value();
    log.trace("received game [{}] from kafka-topic [{}] at {}", game, destination, timestamp);
    final long pongDelta = now.toEpochMilli() - timestamp.toEpochMilli();
    final Game nextRound = gameService.incrementGamesPlayedByOne(
        gameService.increaseTimePongBy(game, pongDelta));
    if (nextRound.getRoundsPlayed() >= nextRound.getRoundsToPlay()) {
      log.info("Last round, game is over. Result: [{}]", nextRound);
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
