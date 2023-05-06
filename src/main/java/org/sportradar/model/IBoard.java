package org.sportradar.model;

import java.util.List;

/**
 * Initial entity to represent different kinds of boards
 */
public interface IBoard {

    /**
     * Returns board id
     *
     * @return exact board id
     */
    long getId();

    /**
     * Return description of the board
     *
     * @return board description
     */
    String getDescription();

    /**
     * Return list of all games included in the board
     * @return full games list
     */
    List<IGame> getGamesList();



}
