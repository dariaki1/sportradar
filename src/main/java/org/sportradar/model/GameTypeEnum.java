package org.sportradar.model;

import java.util.Arrays;

/**
 * Representation of games types available for usage
 */
public enum GameTypeEnum {

    FOOTBALL,
    BASEBALL,
    SOCCER;

    /**
     * Represents case-insensitive search for enum
     *
     * @param value to check
     * @return corresponding enum or null of not found
     */
    static public GameTypeEnum forNameIgnoreCase(String value) {
        return Arrays.stream(GameTypeEnum.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findAny()
                .orElse(null);
    }
}
