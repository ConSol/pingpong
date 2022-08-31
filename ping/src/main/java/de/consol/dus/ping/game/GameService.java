package de.consol.dus.ping.game;

import org.springframework.stereotype.Service;

@Service
public class GameService {

  public Game increaseTimePingBy(Game game, long timePingToAdd) {
    final long timePing = game.getTimePing() + timePingToAdd;
    return game.toBuilder()
        .timePing(timePing)
        .build();
  }

  public Game increaseTimePongBy(Game game, long timePongToAdd) {
    final long timePong = game.getTimePong() + timePongToAdd;
    return game.toBuilder()
        .timePong(timePong)
        .build();
  }

  public Game incrementGamesPlayedByOne(Game game) {
    return game.toBuilder()
        .roundsPlayed(game.getRoundsPlayed() + 1)
        .build();
  }
}
