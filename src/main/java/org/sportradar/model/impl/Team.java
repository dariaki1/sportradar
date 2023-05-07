package org.sportradar.model.impl;

import org.sportradar.model.ITeam;


/**
 * This class represents team entity
 */
public class Team implements ITeam {
//TODO add Builder constructor
    private final long id;
    private String title; //TODO add validation

    private String country; //TODO add validation

    public Team(long id) {
        this.id = id;
    }

    public Team(Builder builder){
        this.id = builder.id;
        this.country = builder.country;
        this.title = builder.title;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public static Builder newBuilder(long id){
        return new Builder(id);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", country='" + country + '\'' +
                '}';
    }

    public static class Builder {

        private final long id;
        private String title;

        private String country;

        private Builder(long id){
            this.id = id;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Team build(){
            return new Team(this);
        }

    }
}
