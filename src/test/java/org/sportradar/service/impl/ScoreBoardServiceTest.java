package org.sportradar.service.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.impl.Game;
import org.sportradar.model.impl.Team;
import org.sportradar.repository.impl.GameRepository;

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

    private long createNewGame(long homeTeamId, long awayTeamId){
        //before
        Team homeTeam = Team.newBuilder(homeTeamId).build();
        Team awayTeam = Team.newBuilder(awayTeamId).build();

        return boardService.createNewFootballGame(homeTeam, awayTeam);
    }
}
