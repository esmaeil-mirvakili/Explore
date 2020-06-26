package net.esmaeil.explore.language;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface LanguageManager {
    String getValue(Language language, String pluginId, String key);

    String getValue(String pluginId, String key);

    boolean exists(Language language, String pluginId, String key);

    boolean exists(String pluginId, String key);

    Map<String, String> getValues(Language language, String pluginId);

    Map<String, String> getValues(String pluginId);

    void add(Language language, String pluginId, String key, String value);

    void add(String pluginId, String key, String value);

    String remove(Language language, String pluginId, String key);

    String remove(String pluginId, String key);

    void removeAll(String pluginId, String key);

    void remove(Language language, String pluginId);

    void remove(String pluginId);

    void removeAll(String pluginId);

    String translate(Language language, String text);

    String translate(String text);

    Language getCurrentLanguage();

    void load(File jsonFile) throws Exception;
}
