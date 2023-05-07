package org.sportradar.util;

import org.sportradar.model.ITeam;

/**
 * Base class for ITeam entity validation
 */
public interface ITeamValidator {

    void validate(ITeam team);
}
