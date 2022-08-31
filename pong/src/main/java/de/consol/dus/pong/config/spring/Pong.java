package de.consol.dus.pong.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.consol.dus.pong")
public class Pong {
  public static void main(String... args) {
    SpringApplication.run(Pong.class, args);
  }
}
