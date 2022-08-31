package de.consol.dus.reporter.report;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameReport {
  UUID gameId;
  long timePing;
  long timePong;
  int roundsPlayed;
  String winner;
}
