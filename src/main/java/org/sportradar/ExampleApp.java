package org.sportradar;

import org.sportradar.model.impl.Team;
import org.sportradar.repository.IGameRepository;
import org.sportradar.repository.impl.GameRepository;
import org.sportradar.service.IGameService;
import org.sportradar.service.IScoreBoardService;
import org.sportradar.service.impl.GameService;
import org.sportradar.service.impl.ScoreBoardService;

import static org.sportradar.model.CountryEnum.*;

/**
 * Here you can find example of ScoreBoard usage
 *
 */
public class ExampleApp
{
    public static void main( String[] args )
    {
        //init
        IGameRepository repository = new GameRepository();
        IGameService gameService = new GameService(repository);
        IScoreBoardService board = new ScoreBoardService(gameService);

        //check title
        System.out.println(board.getScoreBoardTitle());

        //add games
        Team homeTeam = Team.newBuilder(1L).country(MEXICO.name()).build();
        Team awayTeam = Team.newBuilder(2L).country(ARGENTINA.name()).build();
        long gameId1 = board.createNewFootballGame(homeTeam, awayTeam, "new game 1");
        Team homeTeam2 = Team.newBuilder(3L).country(GERMANY.name()).build();
        Team awayTeam2 = Team.newBuilder(4L).country(URUGUAY.name()).build();
        long gameId2 = board.createNewFootballGame(homeTeam2, awayTeam2, "new game 2");

        //update game status
        board.updateGame(gameId1, 5, 2);
        board.updateGame(gameId2, 7, 4);

        //print status
        System.out.println(board.printGamesInProgressSummary());

        //finish games
        board.finishGame(gameId1);
    }
}
