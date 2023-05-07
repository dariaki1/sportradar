package org.sportradar.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.impl.Game;
import org.sportradar.model.impl.Team;
import org.sportradar.repository.impl.GameRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.sportradar.model.GameTypeEnum.BASEBALL;

public class GameServiceTest {

    private static final long TEAM_ID_1 = 1l;
    private static final long TEAM_ID_2 = 2l;
    private GameService gameService;
    private GameRepository gameRepository;

    @Before
    public void before() {
        gameRepository = spy(new GameRepository());
        gameService = new GameService(gameRepository);
    }

    @Test
    public void shouldCreateNewBaseballGame_ignoreCase() {
        //before
        Team homeTeam = Team.newBuilder(TEAM_ID_1).build();
        Team awayTeam = Team.newBuilder(TEAM_ID_2).build();
        String description = "testGame";

        //when
        long newGameId = gameService.createGame(homeTeam, awayTeam, description, "bAsEbaLL");

        //then
        verify(gameRepository).saveOrUpdateGame(argThat((IGame game) -> game.getId() == newGameId));
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.CREATED, resultGame.getStatus());
        assertEquals(GameTypeEnum.BASEBALL, resultGame.getType());
        assertEquals(description, resultGame.getDescription());
        assertEquals(homeTeam, resultGame.getHomeTeam());
        assertEquals(awayTeam, resultGame.getAwayTeam());
        assertEquals((Integer) 0, resultGame.getHomeTeamScore());
        assertEquals((Integer) 0, resultGame.getAwayTeamScore());
    }

    @Test
    public void shouldFailNewGameIfNoSuchGameType() {
        //before
        Team homeTeam = Team.newBuilder(TEAM_ID_1).build();
        Team awayTeam = Team.newBuilder(TEAM_ID_2).build();
        String gameType = "incorrectGameType";

        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            gameService.createGame(homeTeam, awayTeam, null, gameType);
        });

        //then
        String expectedMessage = format("Game type [%s] is not supported", gameType);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameRepository, never()).saveOrUpdateGame(any(IGame.class));
    }

    @Test
    public void shouldFailToUpdateIfGameNotFound() {
        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            gameService.updateGame(3l, 9, 10);
        });

        //then
        String expectedMessage = format("Game with id %d not found", 3l);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameRepository, never()).saveOrUpdateGame(any(IGame.class));
    }

    @Test
    public void shouldUpdateGame() {
        //before
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);

        //when
        long updatedGameId = gameService.updateGame(newGameId, 1, 0);

        //then
        verify(gameRepository, times(2)).saveOrUpdateGame(argThat((IGame game) -> game.getId() == newGameId));
        Game resultGame = (Game) gameRepository.getGameById(updatedGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.STARTED, resultGame.getStatus());
        assertNotNull(resultGame.getStartTime());
        assertEquals(BASEBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer) 1, resultGame.getHomeTeamScore());
        assertEquals((Integer) 0, resultGame.getAwayTeamScore());
    }

    @Test
    public void shouldUpdateGameWithProvidedUpdateTime() {
        //before
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);
        ZonedDateTime updateTime = ZonedDateTime.now();

        //when
        long updatedGameId = gameService.updateGame(newGameId, 1, 0, updateTime);

        //then
        verify(gameRepository, times(2)).saveOrUpdateGame(argThat((IGame game) -> game.getId() == newGameId));
        Game resultGame = (Game) gameRepository.getGameById(updatedGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.STARTED, resultGame.getStatus());
        assertEquals(updateTime, resultGame.getStartTime());
        assertEquals(BASEBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer) 1, resultGame.getHomeTeamScore());
        assertEquals((Integer) 0, resultGame.getAwayTeamScore());
    }

    @Test
    public void shouldFinishStartedGame() {
        //before
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);
        int homeTeamScore = 6;
        int awayTeamScore = 12;
        gameService.updateGame(newGameId, homeTeamScore, awayTeamScore);

        //when
        gameService.finishGame(newGameId);

        //then
        verify(gameRepository, times(3)).saveOrUpdateGame(argThat((IGame game) -> game.getId() == newGameId));
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.FINISHED, resultGame.getStatus());
        assertEquals(BASEBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer) homeTeamScore, resultGame.getHomeTeamScore());
        assertEquals((Integer) awayTeamScore, resultGame.getAwayTeamScore());
    }


    @Test
    public void shouldFailToFinishGameIfGameNotFound() {
        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class,
                () -> gameService.finishGame(3L));

        //then
        String expectedMessage = "Game with id 3 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameRepository, never()).saveOrUpdateGame(any(IGame.class));
    }

    @Test
    public void shouldReturnGamesInProgress(){
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);
        long newGameId2 = createNewGame(3L, 4L);
        long newGameId3 = createNewGame(5L, 6L);
        int homeTeamScore = 6;
        int awayTeamScore = 12;
        gameService.updateGame(newGameId, homeTeamScore, awayTeamScore);
        gameService.updateGame(newGameId2, +1, awayTeamScore+1);
        gameService.finishGame(newGameId2);
        final List<IGame> expected = new ArrayList<>(Arrays.asList(
                gameRepository.getGameById(newGameId).get()));

        //when
        List<IGame> actual = gameService.getGamesInProgressSummary();

        //then
        assertEquals(expected, actual);
    }


    private long createNewGame(long homeTeamId, long awayTeamId) {
        Team homeTeam = Team.newBuilder(homeTeamId).build();
        Team awayTeam = Team.newBuilder(awayTeamId).build();

        return gameService.createGame(homeTeam, awayTeam, null, BASEBALL.name());
    }

}
