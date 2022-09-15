package com.scoreboard;

import com.scoreboard.application.GamesService;
import com.scoreboard.domain.Game;
import com.scoreboard.domain.GameAlreadyFinishedException;
import com.scoreboard.domain.MatchScore;
import com.scoreboard.domain.Team;
import com.scoreboard.infrastructure.InMemoryGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GamesServiceTest {

    private GamesService gamesService = createGamesService();

    private static final Team HOME_TEAM = new Team("home");

    private static final Team AWAY_TEAM = new Team("away");

    @BeforeEach
    public void setUp() {
        this.gamesService = createGamesService();
    }

    @Test
    public void shouldCreateNewGame() {
        //given
        MatchScore matchScore = MatchScore.initialScore();

        //when
        Game game = gamesService.startGame(HOME_TEAM, AWAY_TEAM, LocalDateTime.now());

        //then
        assertThat(game.getHomeTeam()).isEqualTo(HOME_TEAM);
        assertThat(game.getAwayTeam()).isEqualTo(AWAY_TEAM);
        assertThat(game.getMatchScore()).isEqualTo(matchScore);
        assertThat(game.isGameFinished()).isFalse();
    }

    @Test
    public void shouldFinishGame() {
        //given
        Game game = gamesService.startGame(HOME_TEAM, AWAY_TEAM, LocalDateTime.now());

        //when
        Game finishedGame = gamesService.finishGame(game.getGameId());

        //then
        assertThat(finishedGame.isGameFinished()).isTrue();
    }

    @Test
    public void shouldNotFinishGameAlreadyFinishedGame() {
        //given
        Game game = gamesService.startGame(HOME_TEAM, AWAY_TEAM, LocalDateTime.now());
        gamesService.finishGame(game.getGameId());

        //expect
        assertThatThrownBy(() -> gamesService.finishGame(game.getGameId()))
                .isInstanceOf(GameAlreadyFinishedException.class);
    }

    @Test
    public void shouldUpdateMatchScore() {
        //given
        Game game = gamesService.startGame(HOME_TEAM, AWAY_TEAM, LocalDateTime.now());
        MatchScore matchScore = new MatchScore(2, 3);

        //when
        Game finishedGame = gamesService.updateScore(game.getGameId(), matchScore);

        //then
        assertThat(finishedGame.getMatchScore()).isEqualTo(matchScore);
    }

    @Test
    public void shouldNotUpdateMatchScoreOfAlreadyFinishedGame() {
        //given
        Game game = gamesService.startGame(HOME_TEAM, AWAY_TEAM, LocalDateTime.now());
        gamesService.finishGame(game.getGameId());
        MatchScore matchScore = new MatchScore(2, 3);

        //expect
        assertThatThrownBy(() -> gamesService.updateScore(game.getGameId(), matchScore))
                .isInstanceOf(GameAlreadyFinishedException.class);
    }

    @Test
    public void shouldReturnGamesSummary() {
        //given
        LocalDateTime now = LocalDateTime.now();

        MatchScore lowerScore = new MatchScore(1, 2);
        MatchScore biggerScore = new MatchScore(10, 10);

        Game thirdGame = createGame(now, lowerScore);
        Game firstGame = createGame(now, biggerScore);
        Game fourthGame = createGame(now.minusDays(3), lowerScore);
        Game secondGame = createGame(now.minusDays(1), biggerScore);

        //when
        List<Game> orderedGames = gamesService.getSummary()
                .getOrderedGames();

        //then
        assertThat(orderedGames).hasSize(4);
        assertThat(orderedGames.get(0).getGameId()).isEqualTo(firstGame.getGameId());
        assertThat(orderedGames.get(1).getGameId()).isEqualTo(secondGame.getGameId());
        assertThat(orderedGames.get(2).getGameId()).isEqualTo(thirdGame.getGameId());
        assertThat(orderedGames.get(3).getGameId()).isEqualTo(fourthGame.getGameId());
    }

    private Game createGame(LocalDateTime now, MatchScore score) {
        Game game = gamesService.startGame(HOME_TEAM, AWAY_TEAM, now);
        gamesService.updateScore(game.getGameId(), score);
        return game;
    }

    private static GamesService createGamesService() {
        return new GamesService(new InMemoryGameRepository());
    }

}
