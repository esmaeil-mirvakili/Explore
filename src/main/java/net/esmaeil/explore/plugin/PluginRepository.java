package net.esmaeil.explore.plugin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PluginRepository extends CrudRepository<PluginEntity,String> {
    List<PluginEntity> findByStatus(PluginStatus status);
    List<PluginEntity> findByOrderByName();
}
