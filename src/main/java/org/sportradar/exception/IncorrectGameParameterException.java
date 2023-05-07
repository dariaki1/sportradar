package org.sportradar.exception;

/**
 * This exception represent error cases related to incorrect game parameters
 */
public class IncorrectGameParameterException extends IllegalArgumentException {

    /**
     * Constructs a new exception with the specified detail message and
     * cause
     *
     * @return new exception
     */
    public IncorrectGameParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause
     *
     * @return new exception
     */
    public IncorrectGameParameterException(String message) {
        super(message);
    }
}
