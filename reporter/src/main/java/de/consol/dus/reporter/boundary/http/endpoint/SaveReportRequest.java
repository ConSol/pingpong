package de.consol.dus.reporter.boundary.http.endpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class SaveReportRequest {
  String gameId;
  long timePing;
  long timePong;
  int roundsPlayed;
}
