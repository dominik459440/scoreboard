package com.scoreboard.domain;

import java.util.Optional;

public interface GameRepository {

    Game saveGame(Game game);

    Optional<Game> findGame(GameId gameId);

    GamesSummary findGamesSummary();

}
