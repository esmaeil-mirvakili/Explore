package net.esmaeil.explore.language;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.esmaeil.explore.CoreConstants;
import net.esmaeil.explore.config.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LanguageManagerImpl implements LanguageManager {
    private final LanguageEntityRepository languageEntityRepository;
    private final ConfigManager configManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public LanguageManagerImpl(LanguageEntityRepository languageEntityRepository, ConfigManager configManager) {
        this.languageEntityRepository = languageEntityRepository;
        this.configManager = configManager;
    }

    @Override
    public String getValue(Language language, String pluginId, String key) {
        Map<String, String> map = getValues(language, pluginId);
        if (map.containsKey(key))
            return map.get(key);
        return null;
    }

    @Override
    public String getValue(String pluginId, String key) {
        return getValue(getCurrentLanguage(), pluginId, key);
    }

    @Override
    public boolean exists(Language language, String pluginId, String key) {
        return getValue(language, pluginId, key) != null;
    }

    @Override
    public boolean exists(String pluginId, String key) {
        return exists(getCurrentLanguage(), pluginId, key);
    }

    @Override
    public Map<String, String> getValues(Language language, String pluginId) {
        List<LanguageEntity> languageEntities = languageEntityRepository.findByLanguageAndPluginId(language, pluginId);
        Map<String, String> map = new HashMap<>();
        languageEntities.forEach(languageEntity -> map.put(languageEntity.getKey(), languageEntity.getValue()));
        return map;
    }

    @Override
    public Map<String, String> getValues(String pluginId) {
        return getValues(getCurrentLanguage(), pluginId);
    }

    @Override
    public void add(Language language, String pluginId, String key, String value) {
        if (value == null)
            return;
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguage(language);
        languageEntity.setPluginId(pluginId);
        languageEntity.setKey(key);
        languageEntity.setValue(value);
        languageEntityRepository.save(languageEntity);
    }

    @Override
    public void add(String pluginId, String key, String value) {
        add(getCurrentLanguage(), pluginId, key, value);
    }

    @Override
    public String remove(Language language, String pluginId, String key) {
        String result = getValue(language, pluginId, key);
        languageEntityRepository.deleteByLanguageAndPluginIdAndKey(language, pluginId, key);
        return result;
    }

    @Override
    public String remove(String pluginId, String key) {
        return remove(getCurrentLanguage(), pluginId, key);
    }

    @Override
    public void removeAll(String pluginId, String key) {
        for (Language language : Language.values())
            remove(language, pluginId, key);
    }

    @Override
    public void remove(Language language, String pluginId) {
        for (String key : getValues(language, pluginId).keySet())
            remove(language, pluginId, key);
    }

    @Override
    public void remove(String pluginId) {
        remove(getCurrentLanguage(), pluginId);
    }

    @Override
    public void removeAll(String pluginId) {
        for (Language language : Language.values())
            remove(language, pluginId);
    }

    @Override
    public String translate(Language language, String text) {
        Pattern pattern = Pattern.compile("\\{\\{([^:]+):([^:]+)}}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String pluginId = matcher.group(1);
            String key = matcher.group(2);
            String value = getValue(language, pluginId, key);
            if (value != null)
                text = text.replace(matcher.group(), value);
        }
        return text;
    }

    @Override
    public String translate(String text) {
        return translate(getCurrentLanguage(), text);
    }

    @Override
    public Language getCurrentLanguage() {
        try {
            Language language = (Language) configManager.getConfig(CoreConstants.CORE_PLUGIN_ID, CoreConstants.CORE_CURRENT_LANGUAGE);
            return Objects.requireNonNull(language);
        } catch (Exception ignored) {
        }
        return Language.ENGLISH;
    }

    @Override
    public void load(File jsonFile) throws Exception {
        final LanguagePack languagePack = objectMapper.readValue(jsonFile, LanguagePack.class);
        Objects.requireNonNull(languagePack);
        languagePack.getLanguages().forEach((key, value) ->
                add(languagePack.getLanguage(), languagePack.getPluginId(), key, value)
        );
    }
}
