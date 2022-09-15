package com.scoreboard.domain;

public class GameAlreadyFinishedException extends RuntimeException {

    public GameAlreadyFinishedException() {
        super("It's not possible to edit finished game");
    }
}
