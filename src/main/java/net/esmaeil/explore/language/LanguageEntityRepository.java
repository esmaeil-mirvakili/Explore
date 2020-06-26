package net.esmaeil.explore.language;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LanguageEntityRepository extends CrudRepository<LanguageEntity,LanguageEntity.LanguageId> {
    @Transactional
    void deleteByLanguageAndPluginIdAndKey(Language language,String pluginId,String key);

    @Transactional
    void deleteByLanguageAndPluginId(Language language,String pluginId);

    @Transactional
    void deleteByLanguage(Language language);

    Optional<LanguageEntity> findByLanguageAndPluginIdAndKey(Language language,String pluginId,String key);

    List<LanguageEntity> findByLanguageAndPluginId(Language language,String pluginId);

    List<LanguageEntity> findByLanguage(Language language);
}
