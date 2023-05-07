package org.sportradar.util.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.sportradar.exception.IncorrectTeamParameterException;
import org.sportradar.model.impl.Team;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.sportradar.model.CountryEnum.MEXICO;

public class TeamValidatorTest {

    private TeamValidator validator = new TeamValidator();

    @Test
    public void shouldThrowIfCountryIsEmpty() {
        Team homeTeam = Team.newBuilder(1L).country("").build();

        //when
        Exception exception = assertThrows(IncorrectTeamParameterException.class, () -> {
            validator.validate(homeTeam);
        });

        //then
        String expectedMessage = "Error creating team with id 1 :Team country length should be >0 and <=100 characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIfCountryIsTooLong() {
        Team homeTeam = Team.newBuilder(1L).country(StringUtils.repeat('a', 101)).build();

        //when
        Exception exception = assertThrows(IncorrectTeamParameterException.class, () -> {
            validator.validate(homeTeam);
        });

        //then
        String expectedMessage = "Error creating team with id 1 :Team country length should be >0 and <=100 characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void shouldThrowIfTitleIsTooLong() {
        Team homeTeam = Team.newBuilder(1L).country(MEXICO.name())
                .title(StringUtils.repeat('a', 151)).build();

        //when
        Exception exception = assertThrows(IncorrectTeamParameterException.class, () -> {
            validator.validate(homeTeam);
        });

        //then
        String expectedMessage = "Error creating team with id 1 :Team title length should less than 150 characters";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
