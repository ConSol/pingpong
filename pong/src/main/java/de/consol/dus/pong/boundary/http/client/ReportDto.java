package de.consol.dus.pong.boundary.http.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class ReportDto {
  String gameId;
  long timePing;
  long timePong;
  int roundsPlayed;
}