package org.sportradar.model.impl;

import org.sportradar.model.IBoard;
import org.sportradar.model.IGame;

import java.util.List;

/**
 * This entity represent Live Football World Cup Scoreboard example
 */
public class Scoreboard implements IBoard {
    //TODO add Builder constructor

    private final long id;
    private String description;
    private List<IGame> games;

    public Scoreboard(long id){
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<IGame> getGamesList() {
        return games;
    }

}
