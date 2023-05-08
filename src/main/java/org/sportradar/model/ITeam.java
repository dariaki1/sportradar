package org.sportradar.model;

/**
 * Initial entity to represent different kind of teams
 */
public interface ITeam {

    /**
     * Returns team id
     *
     * @return team id
     */
    long getId();

    /**
     * Get team title
     *
     * @return string title
     */
    String getTitle();

    /**
     * Get team country
     *
     * @return team country
     */
    String getCountry();
}
