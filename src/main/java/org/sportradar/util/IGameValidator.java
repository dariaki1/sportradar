package org.sportradar.util;

import org.sportradar.model.IGame;

/**
 * Base class for IGame entity validation
 */
public interface IGameValidator {

    /**
     * Validates IGame object
     * @param game
     */
    void validate(IGame game);
}
