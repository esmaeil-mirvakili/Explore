package net.esmaeil.explore.graphic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface GraphicEntityRepository extends CrudRepository<GraphicEntity,GraphicEntity.GraphicId> {
    @Transactional
    void deleteByPluginIdAndName(String pluginId, String name);

    @Transactional
    void deleteByPluginId(String pluginId);

    Optional<GraphicEntity> findByPluginIdAndName(String pluginId, String name);

    List<GraphicEntity> findByPluginId(String pluginId);
}
