package org.sportradar.exception;

/**
 * This exception represent error cases related to incorrect team parameters
 */
public class IncorrectTeamParameterException extends IllegalArgumentException {

    /**
     * Constructs a new exception with the specified detail message and
     * cause
     */
    public IncorrectTeamParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause
     */
    public IncorrectTeamParameterException(String message) {
        super(message);
    }
}
