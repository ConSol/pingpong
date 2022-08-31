package de.consol.dus.reporter.boundary.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "games", schema = "public")
@Getter
@Setter
public class ReportEntity {
  @Id
  @SequenceGenerator(
      name = "GamesIdGenerator",
      sequenceName = "games__seq__id",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GamesIdGenerator")
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
