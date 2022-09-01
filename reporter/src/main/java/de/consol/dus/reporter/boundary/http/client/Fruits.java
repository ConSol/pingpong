package de.consol.dus.reporter.boundary.http.client;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    return DEFAULT_FRUIT_NAME;
  }
}
