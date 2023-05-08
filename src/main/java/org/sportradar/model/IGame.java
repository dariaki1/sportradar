package org.sportradar.model;

import java.time.ZonedDateTime;

/**
 * Initial entity to represent games
 */
public interface IGame {

    /**
     * Returns game id
     *
     * @return id of exact game
     */
    long getId();

    /**
     * Returns the game type
     *
     * @return enum type of exact game
     */
    GameTypeEnum getType();

    /**
     * Returns status of exact game
     *
     * @return game status
     */
    GameStatusEnum getStatus();

    /**
     * Return home team
     *
     * @return home team
     */
    ITeam getHomeTeam();

    /**
     * Return away team
     *
     * @return away team
     */
    ITeam getAwayTeam();

    /**
     * Return current home team score
     *
     * @return home team score
     */
    Integer getHomeTeamScore();

    /**
     * Set home team score
     *
     * @param homeTeamScore to set
     */
    void setHomeTeamScore(Integer homeTeamScore);

    /**
     * Return current away team score
     *
     * @return away team score
     */
    Integer getAwayTeamScore();

    /**
     * Set away team score
     *
     * @param awayTeamScore to set
     */
    void setAwayTeamScore(Integer awayTeamScore);

    /**
     * Changes game status
     *
     * @param status to set
     */
    void setStatus(GameStatusEnum status);

    /**
     * Get exact time when game changed status to STARTED
     */
    ZonedDateTime getStartTime();

    /**
     * Set start time
     *
     * @param startTime to set
     */
    void setStartTime(ZonedDateTime startTime);

    /**
     * Get game description
     *
     * @return string description
     */
    String getDescription();

}
