package net.esmaeil.explore.exceptions;

public abstract class ExploreException extends Exception {
    public ExploreException(String message) {
        super(message);
    }

    public ExploreException(String message, Throwable cause) {
        super(message, cause);
    }
}
