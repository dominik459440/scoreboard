package com.scoreboard.infrastructure;

import com.scoreboard.domain.Game;
import com.scoreboard.domain.GameId;
import com.scoreboard.domain.GameRepository;
import com.scoreboard.domain.GamesSummary;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryGameRepository implements GameRepository {

    private final ConcurrentHashMap<GameId, Game> games;

    public InMemoryGameRepository() {
        this.games = new ConcurrentHashMap<>();
    }

    @Override
    public Game saveGame(Game game) {
        games.put(game.getGameId(), game);
        return games.get(game.getGameId());
    }

    @Override
    public Optional<Game> findGame(GameId gameId) {
        return Optional.ofNullable(games.get(gameId));
    }

    @Override
    public GamesSummary findGamesSummary() {
        return new GamesSummary(games.values());
    }
}
