package de.consol.dus.reporter.boundary.http.client;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class Fruits {
  private static final String DEFAULT_FRUIT_NAME = "banana";

  private final RestTemplate restTemplate = new RestTemplate();
  private final Random random = new Random();

  private final URI baseUri;

  public Fruits(@Value("${web-clients.fruits.base-url}") URI baseUri) {
    this.baseUri = baseUri;
  }

  public String getRandomFruitName() {
    try {
      final ResponseEntity<Fruit[]> response = restTemplate.getForEntity(baseUri, Fruit[].class);
      if (response.getStatusCode() == HttpStatus.OK) {
        List<String> fruitNames = Arrays.stream(response.getBody())
            .map(Fruit::getName)
            .collect(Collectors.toList());
        if (!fruitNames.isEmpty()) {
          int index = random.nextInt(fruitNames.size());
          return fruitNames.get(index);
        }
      }
    } catch (Exception e) {
      log.error("Error", e);
    }
    log.warn(
        "Unable to get fruit from {}, using default fruit name {}",
        baseUri.toString(),
        DEFAULT_FRUIT_NAME);
    return DEFAULT_FRUIT_NAME;
  }
}
