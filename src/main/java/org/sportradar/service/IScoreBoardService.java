package org.sportradar.service;

import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Initial interface to represent Score board functionality
 */
public interface IScoreBoardService {


    /**
     * Returns list of games in progress ordered by their total score
     *
     * @return list og games ordered s ordered by their total score.
     */
    List<IGame> getGamesInProgressSummary();

    /**
     * Return string representation on current board summary
     *
     * @return string representation of board summary
     */
    String printGamesInProgressSummary();

    /**
     * Creates new game assuming initial score 0 – 0 and adding it the scoreboard
     *
     * @param homeTeam
     * @param awayTeam
     * @return id of created game
     */
    long createNewFootballGame(ITeam homeTeam, ITeam awayTeam);

    /**
     * Creates new game assuming initial score 0 – 0 and adding it the scoreboard
     *
     * @param homeTeam
     * @param awayTeam
     * @param description
     * @return
     */
    long createNewFootballGame(ITeam homeTeam, ITeam awayTeam, String description);

    /**
     * Updates existing game score
     *
     * @param gameId
     * @param homeTeamScore
     * @param awayTeamScore
     * @return id of updated game
     */
    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore);

    /**
     * Updates existing game score
     *
     * @param gameId
     * @param homeTeamScore
     * @param awayTeamScore
     * @param updateTime
     * @return id of updated game
     */
    long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore, ZonedDateTime updateTime);


    /**
     * Finish game which is currently in progress
     *
     * @param gameId
     * @return id of finished game
     */
    long finishGame(long gameId);

    /**
     * Get current board title
     *
     * @return string title
     */
    String getScoreBoardTitle();

}
