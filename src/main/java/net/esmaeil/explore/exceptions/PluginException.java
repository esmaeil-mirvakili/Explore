package net.esmaeil.explore.exceptions;

public class PluginException extends ExploreException {
    private static final String message = "Plugin '%s' failed.";

    public PluginException(String pluginPath) {
        super(String.format(message,pluginPath));
    }

    public PluginException(String pluginPath, Throwable cause) {
        super(String.format(message,pluginPath), cause);
    }
}
