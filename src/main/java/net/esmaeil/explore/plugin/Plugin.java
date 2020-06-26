package net.esmaeil.explore.plugin;

public interface Plugin {
    void install() throws Exception;
    void uninstall() throws Exception;
    void update() throws Exception;
    void enable() throws Exception;
    void disable() throws Exception;
    void load() throws Exception;
    void unload() throws Exception;
}