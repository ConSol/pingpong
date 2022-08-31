package de.consol.dus.ping.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.consol.dus.ping")
public class Ping {
  public static void main(String... args) {
    SpringApplication.run(Ping.class, args);
  }
}
