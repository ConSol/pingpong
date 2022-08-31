package de.consol.dus.pong.game;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class Game {
  @Builder.Default
  UUID id = UUID.randomUUID();
  long timePing;
  long timePong;
  int roundsToPlay;
  int roundsPlayed;
}
