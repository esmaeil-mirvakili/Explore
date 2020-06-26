package net.esmaeil.explore.config;

import java.io.Serializable;
import java.util.Map;

public interface ConfigManager {
    boolean exists(String pluginId, String key);

    Map<String, Serializable> getConfigs(String pluginId);

    void add(String pluginId, String key, Serializable config);

    void remove(String pluginId, String key);

    void remove(String pluginId);

     Serializable getConfig(String pluginId, String key);

     String getConfigAsString(String pluginId, String key);

     Integer getConfigAsInteger(String pluginId, String key);

     Long getConfigAsLong(String pluginId, String key);

     Boolean getConfigAsBoolean(String pluginId, String key);

     Double getConfigAsDouble(String pluginId, String key);

     Float getConfigAsFloat(String pluginId, String key);

     Character getConfigAsCharacter(String pluginId, String key);
}
