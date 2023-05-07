package org.sportradar.service;

import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;

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
     * Creates new game assuming initial score 0 – 0 and adding it the scoreboard
     * @param homeTeam
     * @param awayTeam
     * @return id of created game
     */
    long createNewFootballGame(ITeam homeTeam, ITeam awayTeam);

    long createNewFootballGame(ITeam homeTeam, ITeam awayTeam, String decription);

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
     * Finish game which is currently in progress
     * @param gameId
     * @return id of finished game
     */
    long finishGame(long gameId);





}
