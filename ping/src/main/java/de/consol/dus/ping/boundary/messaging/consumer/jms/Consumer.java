package de.consol.dus.ping.boundary.messaging.consumer.jms;

import de.consol.dus.ping.boundary.http.client.ReportDto;
import de.consol.dus.ping.boundary.http.client.Reporter;
import de.consol.dus.ping.boundary.messaging.producer.kafka.Producer;
import de.consol.dus.ping.game.Game;
import de.consol.dus.ping.game.GameService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class Consumer {
  private final String destination;

  private final GameService gameService;
  private final Producer producer;
  private final Reporter reporter;

  public Consumer(
          @Value("${messaging.activemq.destination}") String destination,
          GameService gameService,
          Producer producer,
          Reporter reporter) {
    this.destination = destination;
    this.gameService = gameService;
    this.producer = producer;
    this.reporter = reporter;
  }


  @JmsListener(
      destination = "${messaging.activemq.destination}",
      containerFactory = "jmsListenerFactory",
      subscription = "${messaging.activemq.subscription}")
  public void consume(
      Game game,
      @Header("CREATION_TIME") long creationTime) {
    final Instant timestamp = Instant.ofEpochMilli(creationTime);
    log.info("received game [{}] from kafka-topic [{}] at {}", game, destination, timestamp);
    final Instant now = Instant.now();
    final long pingDelta = now.toEpochMilli() - timestamp.toEpochMilli();
    final Game nextRound = gameService.incrementGamesPlayedByOne(
        gameService.increaseTimePingBy(game, pingDelta));
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
