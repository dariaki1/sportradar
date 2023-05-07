package org.sportradar.service.impl;

import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;
import org.sportradar.service.IGameService;
import org.sportradar.service.IScoreBoardService;

import java.util.List;

/**
 * Initial implementation of Score Board functionality
 */
public class ScoreBoardService implements IScoreBoardService {

    private IGameService gameService;

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
    public long finishGame(long gameId) {
        return gameService.finishGame(gameId);
    }
}
