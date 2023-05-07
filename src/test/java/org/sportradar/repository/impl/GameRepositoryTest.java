package org.sportradar.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.impl.Game;
import org.sportradar.model.impl.Team;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;

public class GameRepositoryTest {

    private GameRepository gameRepository;

    @Before
    public void before() {
        gameRepository = spy(new GameRepository());
    }

    @Test
    public void shouldReturnEmptyOptionalIfNotFound() {
        //when
        Optional<IGame> result = gameRepository.getGameById(1l);

        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldSaveNewGame() {
        //when
        Team homeTeam = Team.newBuilder(1L).build();
        Team awayTeam = Team.newBuilder(2L).build();
        long gameId = new Random().nextLong();

        IGame newGame = Game.newBuilder(gameId, GameTypeEnum.SOCCER)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();

        //when
        long savedGameId = gameRepository.saveOrUpdateGame(newGame);

        //then
        IGame savedGame = gameRepository.getGameById(savedGameId).get();
        assertEquals(newGame, savedGame);
    }

    @Test
    public void shouldUpdateExistingGame() {
        //when
        Team homeTeam = Team.newBuilder(1L).build();
        Team awayTeam = Team.newBuilder(2L).build();
        long gameId = new Random().nextLong();

        IGame newGame = Game.newBuilder(gameId, GameTypeEnum.SOCCER)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();

        //save
        gameRepository.saveOrUpdateGame(newGame);
        newGame.setStatus(GameStatusEnum.STARTED);
        newGame.setHomeTeamScore(1);

        //when
        long savedGameId = gameRepository.saveOrUpdateGame(newGame);

        //then
        IGame savedGame = gameRepository.getGameById(savedGameId).get();
        assertEquals(newGame, savedGame);
    }

    @Test
    public void shouldClearDatabase() {
        //when
        Team homeTeam = Team.newBuilder(1L).build();
        Team awayTeam = Team.newBuilder(2L).build();
        long gameId = new Random().nextLong();

        IGame newGame = Game.newBuilder(gameId, GameTypeEnum.SOCCER)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();
        gameRepository.saveOrUpdateGame(newGame);

        //when
        gameRepository.flush();

        //then
        Optional<IGame> result = gameRepository.getGameById(gameId);
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnGamesInProgress() {
        //when
        Team homeTeam = Team.newBuilder(1L).build();
        Team awayTeam = Team.newBuilder(2L).build();
        long gameId = new Random().nextLong();

        IGame newGame = Game.newBuilder(gameId, GameTypeEnum.SOCCER)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(4)
                .awayTeamScore(5)
                .status(GameStatusEnum.STARTED)
                .build();
        gameRepository.saveOrUpdateGame(newGame);

        IGame newGame2 = Game.newBuilder(new Random().nextLong(), GameTypeEnum.SOCCER)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();
        gameRepository.saveOrUpdateGame(newGame2);

        final List<IGame> expected = new ArrayList<>(Arrays.asList(newGame));


        //when
        List<IGame> actual = gameRepository.getAllStartedGames();

        //then
        assertEquals(expected, actual);
    }

}
