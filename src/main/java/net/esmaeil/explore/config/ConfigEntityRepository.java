package net.esmaeil.explore.config;

import net.esmaeil.explore.language.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ConfigEntityRepository extends CrudRepository<ConfigEntity,ConfigEntity.ConfigId> {
    @Transactional
    void deleteByPluginIdAndKey(String pluginId, String key);

    @Transactional
    void deleteByPluginId(String pluginId);

    Optional<ConfigEntity> findByPluginIdAndKey(String pluginId, String key);

    List<ConfigEntity> findByPluginId(String pluginId);
}
