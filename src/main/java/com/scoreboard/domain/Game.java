package com.scoreboard.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Game implements Comparable<Game> {

    GameId gameId;

    Team homeTeam;

    Team awayTeam;

    MatchScore matchScore;

    boolean gameFinished;

    LocalDateTime createdDate;

    public Game updateScore(MatchScore newScore) {
        validateScoreUpdate();
        return new Game(
                gameId,
                homeTeam,
                awayTeam,
                newScore,
                gameFinished,
                createdDate
        );
    }

    public Game finishGame() {
        validateGameFinish();
        return new Game(
                gameId,
                homeTeam,
                awayTeam,
                matchScore,
                true,
                createdDate
        );
    }

    public static Game startGame(Team homeTeam, Team awayTeam, LocalDateTime createdDate) {
        return new Game(
                new GameId(),
                homeTeam,
                awayTeam,
                MatchScore.initialScore(),
                false,
                createdDate
        );
    }

    private void validateGameFinish() {
        if (gameFinished) {
            throw new GameAlreadyFinishedException();
        }
    }

    private void validateScoreUpdate() {
        if (gameFinished) {
            throw new GameAlreadyFinishedException();
        }
    }

    @Override
    public int compareTo(Game game) {
        if (matchScore.equals(game.getMatchScore())) {
            return createdDate.compareTo(game.getCreatedDate());
        }
        return matchScore.compareTo(game.getMatchScore());
    }
}
