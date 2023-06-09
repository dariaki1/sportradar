package org.sportradar.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.impl.Game;
import org.sportradar.model.impl.Team;
import org.sportradar.repository.impl.GameRepository;
import org.sportradar.util.IGameValidator;
import org.sportradar.util.ITeamValidator;
import org.sportradar.util.impl.GameValidator;
import org.sportradar.util.impl.TeamValidator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.sportradar.model.CountryEnum.*;
import static org.sportradar.model.GameTypeEnum.FOOTBALL;

public class ScoreBoardServiceTest {

    private static final long TEAM_ID_1 = 1L;
    private static final long TEAM_ID_2 = 2L;
    private ScoreBoardService boardService;
    private GameService gameService;
    private GameRepository gameRepository;
    private final ITeamValidator teamValidator = new TeamValidator();
    private final IGameValidator gameValidator = new GameValidator();

    @Before
    public void before() {
        gameRepository = new GameRepository();
        gameService = spy(new GameService(gameRepository));
        gameService.setTeamValidator(teamValidator);
        gameService.setGameValidator(gameValidator);
        boardService = new ScoreBoardService(gameService);
    }

    @Test
    public void shouldCreateNewFootballGame() {
        //before
        Team homeTeam = Team.newBuilder(TEAM_ID_1).country(URUGUAY.name()).build();
        Team awayTeam = Team.newBuilder(TEAM_ID_2).country(GERMANY.name()).build();

        //when
        long newGameId = boardService.createNewFootballGame(homeTeam, awayTeam);

        //then
        verify(gameService).createGame(eq(homeTeam), eq(awayTeam), isNull(), eq(FOOTBALL.name()));
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.CREATED, resultGame.getStatus());
        assertEquals(FOOTBALL, resultGame.getType());
        assertEquals(homeTeam, resultGame.getHomeTeam());
        assertEquals(awayTeam, resultGame.getAwayTeam());
        assertEquals((Integer) 0, resultGame.getHomeTeamScore());
        assertEquals((Integer) 0, resultGame.getAwayTeamScore());
    }

    @Test
    public void shouldFailNewGameIfHomeTeamIsEmpty() {
        //before
        Team awayTeam = Team.newBuilder(TEAM_ID_2).build();

        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            boardService.createNewFootballGame(null, awayTeam);
        });

        //then
        String expectedMessage = "home team is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameService).createGame(isNull(), eq(awayTeam), isNull(), eq(FOOTBALL.name()));
    }

    @Test
    public void shouldFailNewGameIfAwayTeamIsEmpty() {
        //before
        Team homeTeam = Team.newBuilder(TEAM_ID_2).build();

        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            boardService.createNewFootballGame(homeTeam, null);
        });

        //then
        String expectedMessage = "away team is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameService).createGame(eq(homeTeam), isNull(), isNull(), eq(FOOTBALL.name()));
    }

    @Test
    public void shouldUpdateScore() {
        //before
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);

        //when
        long updatedGameId = boardService.updateGame(newGameId, 1, 0);

        //then
        verify(gameService).updateGame(updatedGameId, 1, 0);
        Game resultGame = (Game) gameRepository.getGameById(updatedGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.STARTED, resultGame.getStatus());
        assertNotNull(resultGame.getStartTime());
        assertEquals(FOOTBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer) 1, resultGame.getHomeTeamScore());
        assertEquals((Integer) 0, resultGame.getAwayTeamScore());

    }

    @Test
    public void shouldFailToUpdateScoreIfGameNotExists() {
        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            boardService.updateGame(3L, 1, 0);
        });

        //then
        String expectedMessage = "Game with id 3 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameService).updateGame(3L, 1, 0);
    }

    @Test
    public void shouldFinishStartedGame() {
        //before
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);
        int homeTeamScore = 6;
        int awayTeamScore = 12;
        boardService.updateGame(newGameId, homeTeamScore, awayTeamScore);

        //when
        boardService.finishGame(newGameId);

        //then
        verify(gameService).finishGame(newGameId);
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.FINISHED, resultGame.getStatus());
        assertEquals(FOOTBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer) homeTeamScore, resultGame.getHomeTeamScore());
        assertEquals((Integer) awayTeamScore, resultGame.getAwayTeamScore());
    }


    @Test
    public void shouldFailToFinishGameIfGameNotFound() {
        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class,
                () -> boardService.finishGame(3L));

        //then
        String expectedMessage = "Game with id 3 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(gameService).finishGame(3L);

    }

    @Test
    public void shouldReturnStartedGamesOrdered() {
        //before
        long teamId3 = 3L;
        long teamId4 = 4L;
        long teamId5 = 5L;
        long teamId6 = 6L;
        long teamId7 = 7L;
        long teamId8 = 8L;
        long game1 = createNewGame(TEAM_ID_1, TEAM_ID_2, MEXICO.name(), CANADA.name(), "game1");
        long game2 = createNewGame(teamId3, teamId4, SPAIN.name(), BRAZIL.name(), "game2");
        long game3 = createNewGame(teamId5, teamId6, GERMANY.name(), FRANCE.name(), "game3");
        long game4 = createNewGame(teamId7, teamId8, URUGUAY.name(), ITALY.name(), "game4");
        long game5 = createNewGame(11L, 12L, ARGENTINA.name(), AUSTRALIA.name(), "game5");

        ZonedDateTime updateTime = ZonedDateTime.now();

        boardService.updateGame(game1, 0, 5, updateTime);
        boardService.updateGame(game2, 10, 2, updateTime.plusMinutes(1));
        boardService.updateGame(game3, 2, 2, updateTime.plusMinutes(2));
        boardService.updateGame(game4, 6, 6, updateTime.plusMinutes(3));
        boardService.updateGame(game5, 3, 1, updateTime.plusMinutes(4));

        final List<IGame> expectedGamesOrdered = new ArrayList<>(Arrays.asList(
                gameRepository.getGameById(game4).get(),
                gameRepository.getGameById(game2).get(),
                gameRepository.getGameById(game1).get(),
                gameRepository.getGameById(game5).get(),
                gameRepository.getGameById(game3).get()));

        //when
        List<IGame> result = boardService.getGamesInProgressSummary();

        //then
        verify(gameService).getGamesInProgressSummary();
        assertEquals(expectedGamesOrdered, result);
    }

    @Test
    public void shouldFilterOutAndReturnOnlyStartedGamesOrdered() {
        //before
        long teamId3 = 3L;
        long teamId4 = 4L;
        long teamId5 = 5L;
        long teamId6 = 6L;
        long teamId7 = 7L;
        long teamId8 = 8L;
        long game1 = createNewGame(TEAM_ID_1, TEAM_ID_2, "game1");
        long game3 = createNewGame(teamId5, teamId6, "game3");
        long game2 = createNewGame(teamId3, teamId4, "game2");
        long game4 = createNewGame(teamId7, teamId8, "game4");

        ZonedDateTime updateTime = ZonedDateTime.now();

        boardService.updateGame(game1, 0, 5, updateTime);
        boardService.updateGame(game2, 6, 6, updateTime.plusMinutes(1));
        boardService.updateGame(game3, 10, 2, updateTime.plusMinutes(2));
        boardService.updateGame(game4, 15, 10, updateTime.plusMinutes(3));

        boardService.finishGame(game4);

        final List<IGame> expectedGamesOrdered = new ArrayList<>(Arrays.asList(
                gameRepository.getGameById(game3).get(),
                gameRepository.getGameById(game2).get(),
                gameRepository.getGameById(game1).get()));

        //when
        List<IGame> result = boardService.getGamesInProgressSummary();

        //then
        assertEquals(expectedGamesOrdered, result);
    }

    @Test
    public void shouldReturnTitle() {
        assertEquals("Live Football World Cup Scoreboard", boardService.getScoreBoardTitle());
    }

    @Test
    public void shouldReturnBoardSummaryAsString() {
        //when
        Team homeTeam = Team.newBuilder(1L).country(MEXICO.name()).build();
        Team awayTeam = Team.newBuilder(2L).country(ARGENTINA.name()).build();
        long gameId1 = boardService.createNewFootballGame(homeTeam, awayTeam, "new game 1");
        Team homeTeam2 = Team.newBuilder(3L).country(GERMANY.name()).build();
        Team awayTeam2 = Team.newBuilder(4L).country(URUGUAY.name()).build();
        long gameId2 = boardService.createNewFootballGame(homeTeam2, awayTeam2, "new game 2");
        boardService.updateGame(gameId1, 5, 2);
        boardService.updateGame(gameId2, 7, 4);

        //when
        String actual = boardService.printGamesInProgressSummary();

        //then
        String expected = "Live Football World Cup Scoreboard summary:\n" +
                "\n" +
                "GERMANY 7 - URUGUAY 4\n" +
                "MEXICO 5 - ARGENTINA 2\n";
        assertEquals(expected, actual);
    }


    private long createNewGame(long homeTeamId, long awayTeamId) {
        return createNewGame(homeTeamId, awayTeamId, null);
    }

    private long createNewGame(long homeTeamId, long awayTeamId, String description) {
        return createNewGame(homeTeamId, awayTeamId, AUSTRALIA.name(), MEXICO.name(), description);
    }

    private long createNewGame(long homeTeamId, long awayTeamId, String homeCountry, String awayCountry, String description) {
        Team homeTeam = Team.newBuilder(homeTeamId).country(homeCountry).build();
        Team awayTeam = Team.newBuilder(awayTeamId).country(awayCountry).build();

        return boardService.createNewFootballGame(homeTeam, awayTeam, description);
    }

    @After
    public void after() {
        gameRepository.flush();
    }
}
