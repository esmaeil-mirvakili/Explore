package net.esmaeil.explore.exceptions;

public class InternalExploreException extends ExploreException {
    private static final String message = "Internal exception.";

    public InternalExploreException() {
        super(message);
    }

    public InternalExploreException(Throwable cause) {
        super(message, cause);
    }
}
