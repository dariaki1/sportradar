package org.sportradar.model;

/**
 * Initial entity to represent games
 */
public interface IGame {

    /**
     * Returns game id
     *
     * @return
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
     * @return game status
     */
    GameStatusEnum getStatus();

    public ITeam getHomeTeam();


    public ITeam getAwayTeam();

    public Integer getHomeTeamScore();

    public void setHomeTeamScore(Integer homeTeamScore);

    public Integer getAwayTeamScore();

    public void setAwayTeamScore(Integer awayTeamScore) ;

    public void setStatus(GameStatusEnum status);

}
