package com.scoreboard.domain;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(GameId gameId) {
        super("Game with id: " + gameId.getId() + " not found.");
    }
}
