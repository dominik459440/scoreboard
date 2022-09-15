package com.scoreboard.application;


import com.scoreboard.domain.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GamesService {

    private final GameRepository gameRepository;

    public Game startGame(Team homeTeam, Team awayTeam, LocalDateTime startTime) {
        Game game = Game.startGame(homeTeam, awayTeam, startTime);
        gameRepository.saveGame(game);
        return game;
    }

    public Game finishGame(GameId gameId) {
        Game game = gameRepository.findGame(gameId)
                .map(Game::finishGame)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        return gameRepository.saveGame(game);
    }

    public Game updateScore(GameId gameId, MatchScore newScore) {
        Game updatedGame = gameRepository.findGame(gameId)
                .map(game -> game.updateScore(newScore))
                .orElseThrow(() -> new GameNotFoundException(gameId));
        return gameRepository.saveGame(updatedGame);
    }

    public GamesSummary getSummary() {
        return gameRepository.findGamesSummary();
    }

}
