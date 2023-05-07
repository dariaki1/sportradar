package org.sportradar.util.impl;

import org.apache.commons.lang3.StringUtils;
import org.sportradar.exception.IncorrectTeamParameterException;
import org.sportradar.model.ITeam;
import org.sportradar.util.ITeamValidator;

import static java.lang.String.format;

/**
 * Basic implementation of ITeam validation
 */
public class TeamValidator implements ITeamValidator {

    public static final int TEAM_COUNTRY_MAX_LENGTH = 100;
    public static final int TEAM_TITLE_MAX_LENGTH = 150;
    public static final int TEAM_COUNTRY_MIN_LENGTH = 0;
    @Override
    public void validate(ITeam team) {
            String country = team.getCountry();
            if (StringUtils.isBlank(country)
                    || (country.length() == TEAM_COUNTRY_MIN_LENGTH) || (country.length() > TEAM_COUNTRY_MAX_LENGTH)) {
                throw new IncorrectTeamParameterException(
                        format("Error creating team with id %d :Team country length should be >%d and <=%d characters",
                                team.getId(), TEAM_COUNTRY_MIN_LENGTH, TEAM_COUNTRY_MAX_LENGTH));
            }

            String title = team.getTitle();
            if (StringUtils.isNotBlank(title)
                    && title.length() > TEAM_TITLE_MAX_LENGTH) {
                throw new IncorrectTeamParameterException(
                        format("Error creating team with id %d :Team title length should less than %d characters", team.getId(), TEAM_TITLE_MAX_LENGTH));
            }

    }
}
