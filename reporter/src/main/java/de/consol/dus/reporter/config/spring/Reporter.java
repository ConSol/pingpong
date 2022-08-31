package de.consol.dus.reporter.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "de.consol.dus.reporter")
@EnableJpaRepositories("de.consol.dus.reporter.boundary.persistence")
@EntityScan("de.consol.dus.reporter.boundary.persistence")
public class Reporter {
  public static void main(String... args) {
    SpringApplication.run(Reporter.class, args);
  }
}
