package org.sportradar.service;

import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;

import java.util.List;

public interface IGameService {

    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore);

    long finishGame(long gameId);

    public List<IGame> getGamesInProgressSummary();

    long createGame(ITeam homeTeam, ITeam awayTeam, String description, String string);
}
