package org.sportradar.model.impl;

import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;
import org.sportradar.model.GameTypeEnum;


/**
 * This class represents games of specific {@link GameTypeEnum#FOOTBALL} type
 */
public class Game implements IGame {

    private final long id;
    private String description;
    private GameTypeEnum type;

    private GameStatusEnum status;

    private ITeam homeTeam;

    private ITeam awayTeam;
    private Integer homeTeamScore; //add validation
    private Integer awayTeamScore;// add validation

//    public FootballGame(long id) {
//        this.id = id;
//        type = GameTypeEnum.FOOTBALL;
//    }

    public Game(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.status = builder.status;
        this.description = builder.description;
        this.awayTeam = builder.awayTeam;
        this.homeTeam = builder.homeTeam;
        this.awayTeamScore = builder.awayTeamScore;
        this.homeTeamScore = builder.homeTeamScore;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public GameTypeEnum getType() {
        return type;
    }

    @Override
    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ITeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(ITeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public ITeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(ITeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    @Override
    public String toString() {
        return "FootballGame{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                '}';
    }

    public static Builder newBuilder(long id, GameTypeEnum type) {
        return new Builder(id, type);
    }

    public static class Builder {

        private long id;
        private String description;
        private GameTypeEnum type;

        private GameStatusEnum status;

        private ITeam homeTeam;

        private ITeam awayTeam;
        private Integer homeTeamScore; //add validation
        private Integer awayTeamScore;// add validation

        private Builder(long id, GameTypeEnum type) {
            this.id = id;
            this.type = type;
            this.status = GameStatusEnum.CREATED;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder homeTeam(ITeam homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder awayTeam(ITeam awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public Builder homeTeamScore(Integer score) {
            this.homeTeamScore = score;
            return this;
        }

        public Builder awayTeamScore(Integer score) {
            this.awayTeamScore = score;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }


}
