package org.sportradar.service.impl;

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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createNewGame(ITeam homeTeam, ITeam awayTeam) {
        return gameService.createGame(homeTeam, awayTeam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long finishGame(long gameId) {
        return 0;
    }
}
