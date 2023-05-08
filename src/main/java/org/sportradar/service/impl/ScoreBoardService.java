package org.sportradar.service.impl;

import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;
import org.sportradar.service.IGameService;
import org.sportradar.service.IScoreBoardService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Initial implementation of Score Board functionality
 */
public class ScoreBoardService implements IScoreBoardService {

    private static final String SCOREBOARD_TITLE = "Live Football World Cup Scoreboard";
    private final IGameService gameService;

    public ScoreBoardService(IGameService gameService) {
        this.gameService = gameService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IGame> getGamesInProgressSummary() {
        return gameService.getGamesInProgressSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String printGamesInProgressSummary() {
        List<IGame> gamesInProgress = getGamesInProgressSummary();

        String gamesSummary = gamesInProgress.stream()
                .map(game -> {
                    return String.format("%s %d - %s %d\n",
                            game.getHomeTeam().getCountry(),
                            game.getHomeTeamScore(),
                            game.getAwayTeam().getCountry(),
                            game.getAwayTeamScore());

                }).collect(Collectors.joining());

        return SCOREBOARD_TITLE + " summary:\n\n" + gamesSummary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createNewFootballGame(ITeam homeTeam, ITeam awayTeam) {
        return createNewFootballGame(homeTeam, awayTeam, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createNewFootballGame(ITeam homeTeam, ITeam awayTeam, String description) {
        return gameService.createGame(homeTeam, awayTeam, description, GameTypeEnum.FOOTBALL.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore) {
        return gameService.updateGame(gameId, homeTeamScore, awayTeamScore);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore, ZonedDateTime updateTime) {
        return gameService.updateGame(gameId, homeTeamScore, awayTeamScore, updateTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long finishGame(long gameId) {
        return gameService.finishGame(gameId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getScoreBoardTitle() {
        return SCOREBOARD_TITLE;
    }
}
