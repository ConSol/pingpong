package de.consol.dus.reporter.boundary.http.client;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class Fruit {
  String name;
}
