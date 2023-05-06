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

    String getTitle();

    String getCountry();
}
