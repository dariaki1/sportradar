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

}
