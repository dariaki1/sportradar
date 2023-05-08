package org.sportradar.service;

import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;

import java.time.ZonedDateTime;
import java.util.List;

public interface IGameService {

    /**
     * Update existing game
     *
     * @param gameId
     * @param homeTeamScore
     * @param awayTeamScore
     * @return id of updated game
     */
    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore);

    /**
     * Update existing game
     *
     * @param gameId
     * @param homeTeamScore
     * @param awayTeamScore
     * @param updateTime
     * @return id of updated game
     */
    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore, ZonedDateTime updateTime);

    /**
     * Changes game status to FINISHED
     *
     * @param gameId
     * @return id of updated game
     */
    long finishGame(long gameId);

    /**
     * Return list of games in STARTED status
     *
     * @return
     */
    List<IGame> getGamesInProgressSummary();

    /**
     * Creates new game
     *
     * @param homeTeam
     * @param awayTeam
     * @param description
     * @param string
     * @return id of created game
     */
    long createGame(ITeam homeTeam, ITeam awayTeam, String description, String string);
}
