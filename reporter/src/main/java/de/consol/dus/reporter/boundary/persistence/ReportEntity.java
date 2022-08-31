package de.consol.dus.reporter.boundary.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "games")
@Getter
@Setter
public class ReportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "game_id", nullable = false)
  private String gameId;

  @Column(name = "time_ping", nullable = false)
  private long timePing;

  @Column(name = "time_pong", nullable = false)
  private long timePong;

  @Column(name = "rounds_played", nullable = false)
  private int roundsPlayed;

  @Column(name = "winner")
  private String winner;
}
