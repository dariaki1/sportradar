package org.sportradar.service;

import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;

import java.time.ZonedDateTime;
import java.util.List;

public interface IGameService {

    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore);

    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore, ZonedDateTime updateTime);

    long finishGame(long gameId);

    List<IGame> getGamesInProgressSummary();

    long createGame(ITeam homeTeam, ITeam awayTeam, String description, String string);
}
