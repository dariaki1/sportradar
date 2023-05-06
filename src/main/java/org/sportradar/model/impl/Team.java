package org.sportradar.model.impl;

import lombok.AllArgsConstructor;
import org.sportradar.model.ITeam;


/**
 * This class represents team entity
 */
@AllArgsConstructor
public class Team implements ITeam {

    private final long id;
    private String title; //TODO add validation

    private String country; //TODO add validation
}
