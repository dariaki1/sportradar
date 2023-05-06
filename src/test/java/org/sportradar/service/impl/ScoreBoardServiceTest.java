package org.sportradar.service.impl;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.impl.Game;
import org.sportradar.model.impl.Team;
import org.sportradar.repository.impl.GameRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ScoreBoardServiceTest {

    private static final long TEAM_ID_1 = 1l;
    private static final long TEAM_ID_2 = 2l;


    private static ScoreBoardService boardService;

    private static GameService gameService;
    private static GameRepository gameRepository;

    @BeforeClass
    public static void beforeClass(){
        gameRepository= new GameRepository();
        gameService = new GameService(gameRepository);

        boardService = new ScoreBoardService(gameService);
    }

    @Test
    public void shouldCreateNewFootballGame(){
        //before
        Team homeTeam = Team.newBuilder(TEAM_ID_1).build();
        Team awayTeam = Team.newBuilder(TEAM_ID_2).build();

        //when
        long newGameId = boardService.createNewFootballGame(homeTeam, awayTeam);

        //then
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.CREATED, resultGame.getStatus());
        assertEquals(GameTypeEnum.FOOTBALL, resultGame.getType());
        assertEquals(homeTeam, resultGame.getHomeTeam());
        assertEquals(awayTeam, resultGame.getAwayTeam());
        assertEquals((Integer)0, resultGame.getHomeTeamScore());
        assertEquals((Integer)0, resultGame.getAwayTeamScore());
    }

    @Test
    public void shouldFailNewGameIfHomeTeamIsEmpty(){
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
    }

    @Test
    public void shouldFailNewGameIfAwayTeamIsEmpty(){
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
    }

    @Test
    public void shouldUpdateScore(){
        //before
        long newGameId = createNewGame(TEAM_ID_1, TEAM_ID_2);

        //when
        long updatedGameId = boardService.updateGame(newGameId, 1, 0);

        //then
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.STARTED, resultGame.getStatus());
        assertNotNull(resultGame.getStartTime());
        assertEquals(GameTypeEnum.FOOTBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer)1, resultGame.getHomeTeamScore());
        assertEquals((Integer)0, resultGame.getAwayTeamScore());
    }

    @Test
    public void shouldFailToUpdateScoreIfGameNotExists(){
        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            boardService.updateGame(3L, 1, 0);
        });

        //then
        String expectedMessage = "Game with id 3 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
        Game resultGame = (Game) gameRepository.getGameById(newGameId).get();
        assertEquals(newGameId, resultGame.getId());
        assertEquals(GameStatusEnum.FINISHED, resultGame.getStatus());
        assertEquals(GameTypeEnum.FOOTBALL, resultGame.getType());
        assertEquals(TEAM_ID_1, resultGame.getHomeTeam().getId());
        assertEquals(TEAM_ID_2, resultGame.getAwayTeam().getId());
        assertEquals((Integer)homeTeamScore, resultGame.getHomeTeamScore());
        assertEquals((Integer)awayTeamScore, resultGame.getAwayTeamScore());
    }


    @Test
    public void shouldFailToFinishGameIfGameNotFound() {
        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            boardService.finishGame(3L);
        });

        //then
        String expectedMessage = "Game with id 3 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldReturnStartedGamesOrdered() throws InterruptedException {
        //before
        long teamId3 = 3L;
        long teamId4 = 4l;
        long teamId5 = 5L;
        long teamId6 = 6L;
        long teamId7 = 7l;
        long teamId8 = 8L;
        long game1 = createNewGame(TEAM_ID_1, TEAM_ID_2);
        long game2 = createNewGame(teamId3, teamId4);
        long game3 = createNewGame(teamId5, teamId6);
        long game4 = createNewGame(teamId7, teamId8);

        boardService.updateGame(game1, 0, 5);
        TimeUnit.SECONDS.sleep(1);
        boardService.updateGame(game2, 6, 6);
        TimeUnit.SECONDS.sleep(1);
        boardService.updateGame(game3, 10, 2);
        TimeUnit.SECONDS.sleep(1);
        boardService.updateGame(game4, 15, 10);

        boardService.finishGame(game4);

        final List<IGame> expectedGamesOrdered = new ArrayList<>(Arrays.asList(
                gameRepository.getGameById(game2).get(),
                gameRepository.getGameById(game3).get(),
                gameRepository.getGameById(game1).get()));

        //when
        List<IGame> result = boardService.getGamesInProgressSummary();

        //then
        assertEquals(expectedGamesOrdered, result);
        //assertTrue(CollectionUtils.isEqualCollection(result, expectedGamesOrdered));





    }



    private long createNewGame(long homeTeamId, long awayTeamId){
        //before
        Team homeTeam = Team.newBuilder(homeTeamId).build();
        Team awayTeam = Team.newBuilder(awayTeamId).build();

        return boardService.createNewFootballGame(homeTeam, awayTeam);
    }

    @After
    public void after(){
        gameRepository.flush();
    }
}
