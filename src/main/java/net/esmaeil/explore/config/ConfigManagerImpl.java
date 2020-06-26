package net.esmaeil.explore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigManagerImpl implements ConfigManager {
    private final ConfigEntityRepository configEntityRepository;

    @Autowired
    public ConfigManagerImpl(ConfigEntityRepository configEntityRepository) {
        this.configEntityRepository = configEntityRepository;
    }

    @Override
    public boolean exists(String pluginId, String key) {
        return getConfig(pluginId, key) != null;
    }

    @Override
    public Map<String, Serializable> getConfigs(String pluginId) {
        List<ConfigEntity> configEntities = configEntityRepository.findByPluginId(pluginId);
        Map<String, Serializable> map = new HashMap<>();
        configEntities.forEach(configEntity -> map.put(configEntity.getKey(), configEntity.getConfig()));
        return map;
    }

    @Override
    public void add(String pluginId, String key, Serializable config) {
        if (config == null)
            return;
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setPluginId(pluginId);
        configEntity.setKey(key);
        configEntity.setConfig(config);
        configEntityRepository.save(configEntity);
    }

    @Override
    public void remove(String pluginId, String key) {
        configEntityRepository.deleteByPluginIdAndKey(pluginId, key);
    }

    @Override
    public void remove(String pluginId) {
        configEntityRepository.deleteByPluginId(pluginId);
    }

    @Override
    public Serializable getConfig(String pluginId, String key) {
        return configEntityRepository.findByPluginIdAndKey(pluginId,key).orElseGet(() -> {
            ConfigEntity configEntity = new ConfigEntity();
            configEntity.setConfig(null);
            return configEntity;
        }).getConfig();
    }

    @Override
    public String getConfigAsString(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof String)
            return (String) config;
        return null;
    }

    @Override
    public Integer getConfigAsInteger(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof Integer)
            return (Integer) config;
        return null;
    }

    @Override
    public Long getConfigAsLong(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof Long)
            return (Long) config;
        return null;
    }

    @Override
    public Boolean getConfigAsBoolean(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof Boolean)
            return (Boolean) config;
        return null;
    }

    @Override
    public Double getConfigAsDouble(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof Double)
            return (Double) config;
        return null;
    }

    @Override
    public Float getConfigAsFloat(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof Float)
            return (Float) config;
        return null;
    }

    @Override
    public Character getConfigAsCharacter(String pluginId, String key) {
        Serializable config = getConfig(pluginId, key);
        if(config == null)
            return null;
        if(config instanceof Character)
            return (Character) config;
        return null;
    }
}
