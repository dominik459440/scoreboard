package com.scoreboard.domain;

import lombok.Value;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class GamesSummary {

    List<Game> orderedGames;

    public GamesSummary(Collection<Game> games) {
        this.orderedGames = Collections.unmodifiableList(games.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()));
    }


}
