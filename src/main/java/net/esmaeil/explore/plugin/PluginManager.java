package net.esmaeil.explore.plugin;

import net.esmaeil.explore.exceptions.ExploreException;

import java.io.File;
import java.util.List;

public interface PluginManager {
    void install(String pluginPath) throws ExploreException;
    void uninstall(String pluginId) throws ExploreException;
    void update(String pluginId,String path) throws ExploreException;
    void disable(String pluginId) throws ExploreException;
    void enable(String pluginId) throws ExploreException;
    void load(String pluginId) throws ExploreException;
    void unload(String pluginId) throws ExploreException;
    List<PluginEntity> installedPlugins() throws ExploreException;
    List<PluginEntity> availablePlugins() throws ExploreException;
}
