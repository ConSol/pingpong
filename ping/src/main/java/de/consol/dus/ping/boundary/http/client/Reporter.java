package de.consol.dus.ping.boundary.http.client;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class Reporter {
  private final WebClient reporterWebClient;

  public Reporter(@Value("${web-clients.reporter.base-url}") URI baseUrl) {
    reporterWebClient = WebClient.builder()
        .baseUrl(baseUrl.toString())
        .build();
  }

  public void postReport(ReportDto report) {
    reporterWebClient.post()
        .bodyValue(report)
        .retrieve();
  }
}
