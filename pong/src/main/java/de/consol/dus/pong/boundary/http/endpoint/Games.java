package de.consol.dus.pong.boundary.http.endpoint;

import de.consol.dus.pong.boundary.messaging.producer.jms.Producer;
import de.consol.dus.pong.game.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
@Log4j2
@RequiredArgsConstructor
public class Games {
  private static final int DEFAULT_ROUNDS = 10;

  private final Producer producer;

  @PostMapping(consumes =  MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> send(@RequestBody String roundsAsString) {
    final Game game = Game.builder()
        .roundsToPlay(parseInt(roundsAsString))
        .build();
    producer.send(game);
    return ResponseEntity.noContent()
        .header("X-Correlation-ID", game.getId().toString())
        .build();
  }

  private static int parseInt(String intAsString) {
    try {
      return Integer.parseInt(intAsString);
    } catch (NumberFormatException e) {
      log.error(
          "Unable to parse [{}] to an int, using default-value [{}] instead.",
          intAsString,
          DEFAULT_ROUNDS);
      return DEFAULT_ROUNDS;
    }
  }
}
