package org.sportradar.service;

import org.sportradar.model.ITeam;

public interface IGameService {

    long createGame(ITeam homeTeam, ITeam awayTeam, String gameType);

    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore);

    long finishGame(long gameId);
}
