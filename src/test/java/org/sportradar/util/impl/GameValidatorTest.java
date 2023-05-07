package org.sportradar.util.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.impl.Game;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class GameValidatorTest {

    private GameValidator validator = new GameValidator();

    @Test
    public void shouldThrowIfHomeScoreLessThatZero() {
        //when
        IGame newGame = Game.newBuilder(1L, GameTypeEnum.FOOTBALL)
                .homeTeam(null)
                .awayTeam(null)
                .homeTeamScore(-1)
                .awayTeamScore(0)
                .description(null)
                .build();

        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            validator.validate(newGame);
        });

        //then
        String expectedMessage = "Error creating game with id 1 : Game home team score should be >= 0";
        //String expectedMessage = "Error creating game with id 1 : Game description length should be empty or between 0 and 150 characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIfAwayScoreLessThatZero() {
        //when
        IGame newGame = Game.newBuilder(1L, GameTypeEnum.FOOTBALL)
                .homeTeam(null)
                .awayTeam(null)
                .homeTeamScore(0)
                .awayTeamScore(-1)
                .description(null)
                .build();

        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            validator.validate(newGame);
        });

        //then
        String expectedMessage = "Error creating game with id 1 : Game away team score should be >= 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIfDescriptionIsTooLong() {
        //when
        IGame newGame = Game.newBuilder(1L, GameTypeEnum.FOOTBALL)
                .homeTeam(null)
                .awayTeam(null)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .description(StringUtils.repeat('a', 151))
                .build();

        //when
        Exception exception = assertThrows(IncorrectGameParameterException.class, () -> {
            validator.validate(newGame);
        });

        //then
        String expectedMessage = "Error creating game with id 1 : Game description length should be empty or between 0 and 150 characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
