package net.esmaeil.explore.exceptions;

public class PluginNotFoundException extends ExploreException {
    private static final String message = "Plugin not found.";

    public PluginNotFoundException() {
        super(message);
    }

    public PluginNotFoundException(Throwable cause) {
        super(message, cause);
    }
}
