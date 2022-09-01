package de.consol.dus.pong.boundary.http.client;

import java.net.URI;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class Reporter {
  private final RestTemplate restTemplate = new RestTemplate();
  private final URI baseUrl;

  public Reporter(@Value("${web-clients.reporter.base-url}") URI baseUrl) {
    this.baseUrl = baseUrl;
  }

  public void postReport(ReportDto report) {
    restTemplate.postForLocation(baseUrl, new HttpEntity<>(report));
  }
}
