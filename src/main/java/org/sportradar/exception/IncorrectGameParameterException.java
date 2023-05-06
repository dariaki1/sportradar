package org.sportradar.exception;

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

    public IncorrectGameParameterException(String message) {
        super(message);
    }
}
