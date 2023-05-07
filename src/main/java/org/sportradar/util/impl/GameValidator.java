package org.sportradar.util.impl;

import org.apache.commons.lang3.StringUtils;
import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.IGame;
import org.sportradar.util.IGameValidator;

import static java.lang.String.format;

/**
 * Basic implementation of IGame validation
 */
public class GameValidator implements IGameValidator {

    public static final int GAME_DESCRIPTION_MAX_LENGTH = 150;
    public static final int GAME_DESCRIPTION_MIN_LENGTH = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(IGame game) {
        String description = game.getDescription();
        if (StringUtils.isNotBlank(description)
                && ((description.length() < GAME_DESCRIPTION_MIN_LENGTH) || (description.length() > GAME_DESCRIPTION_MAX_LENGTH))) {
            throw new IncorrectGameParameterException(
                    format("Error creating game with id %d : Game description length should be empty or between %d and %d characters",
                            game.getId(), GAME_DESCRIPTION_MIN_LENGTH, GAME_DESCRIPTION_MAX_LENGTH));
        }
        if (game.getHomeTeamScore() < 0) {
            throw new IncorrectGameParameterException(
                    format("Error creating game with id %d : Game home team score should be >= 0",
                            game.getId()));
        }
        if (game.getAwayTeamScore() < 0) {
            throw new IncorrectGameParameterException(
                    format("Error creating game with id %d : Game away team score should be >= 0",
                            game.getId()));
        }
    }
}
