package com.scoreboard.domain;

import lombok.Value;

@Value
public class MatchScore implements Comparable<MatchScore> {

    Integer homeTeamScore;

    Integer awayTeamScore;

    public static MatchScore initialScore() {
        return new MatchScore(0, 0);
    }

    @Override
    public int compareTo(MatchScore matchScore) {
        return getTotalScore()
                .compareTo(matchScore.getTotalScore());
    }

    public Integer getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }
}
