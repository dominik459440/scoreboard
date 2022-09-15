package com.scoreboard.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class GameId {

    String id;

    public GameId() {
        this.id = UUID.randomUUID().toString();
    }

}
