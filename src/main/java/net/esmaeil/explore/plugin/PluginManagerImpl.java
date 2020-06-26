package net.esmaeil.explore.plugin;

import net.esmaeil.explore.CoreEvents;
import net.esmaeil.explore.event.Event;
import net.esmaeil.explore.event.EventManager;
import net.esmaeil.explore.exceptions.ExploreException;
import net.esmaeil.explore.exceptions.PluginException;
import net.esmaeil.explore.exceptions.PluginNotFoundException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public final class PluginManagerImpl implements PluginManager,InitializingBean,DisposableBean {
    private final EventManager eventManager;
    private final PluginRepository pluginRepository;
    private final String pluginsRepoPath;
    private final String pluginsInstallPath;

    @Autowired
    public PluginManagerImpl(EventManager eventManager, PluginRepository pluginRepository, @Value("${explore.plugin.repository.path}") String pluginsRepoPath,@Value("${explore.plugin.install.path}") String pluginsInstallPath) {
        this.eventManager = eventManager;
        this.pluginRepository = pluginRepository;
        this.pluginsRepoPath = pluginsRepoPath;
        this.pluginsInstallPath = pluginsInstallPath;
    }

    @Override
    public void install(String pluginPath) throws ExploreException {
        try {
            PluginEntity pluginEntity = PluginUtils.loadPlugin(pluginPath);
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.BEFORE_INSTALL_PLUGIN);
            Objects.requireNonNull(pluginEntity).setPath(PluginUtils.copy(pluginPath,pluginsInstallPath).getPath());
            Plugin plugin = PluginUtils.getPlugin(pluginEntity);
            plugin.install();
            Objects.requireNonNull(pluginEntity).setStatus(PluginStatus.ENABLED);
            pluginRepository.save(pluginEntity);
            load(pluginEntity.getId());
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.AFTER_INSTALL_PLUGIN);
        } catch (Exception e) {
            throw new PluginException(pluginPath, e);
        }
    }

    @Override
    public void uninstall(String pluginId) throws ExploreException {
        PluginEntity pluginEntity = null;
        try {
            pluginEntity = pluginRepository.findById(pluginId).orElseThrow(PluginNotFoundException::new);
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.BEFORE_UNINSTALL_PLUGIN);
            unload(pluginId);
            Plugin plugin = PluginUtils.getPlugin(Objects.requireNonNull(pluginEntity));
            plugin.uninstall();
            pluginRepository.delete(pluginEntity);
            new File(pluginEntity.getPath()).delete();
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.AFTER_UNINSTALL_PLUGIN);
        }catch (Exception ex){
            if(pluginEntity != null)
            pluginRepository.save(pluginEntity);
            load(pluginId);
            throw new PluginException(pluginId, ex);
        }
    }

    @Override
    public synchronized void update(String pluginId,String newPluginPath) throws ExploreException {
        File temp = null;
        File installed = null;
        PluginEntity pluginEntity = null;
        try {
            pluginEntity = pluginRepository.findById(pluginId).orElseThrow(PluginNotFoundException::new);
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.BEFORE_UPDATE_PLUGIN);
            unload(pluginId);
            disable(pluginId);
            temp = PluginUtils.rename(Objects.requireNonNull(pluginEntity).getPath(),"plugin_update");
            installed = PluginUtils.copy(newPluginPath,pluginsInstallPath);
            PluginEntity newPluginEntity = PluginUtils.loadPlugin(installed.getPath());
            Plugin plugin = PluginUtils.getPlugin(Objects.requireNonNull(newPluginEntity));
            pluginRepository.save(Objects.requireNonNull(newPluginEntity));
            plugin.update();
            temp.delete();
            eventManager.event(Event.newEvent(newPluginEntity),CoreEvents.AFTER_UPDATE_PLUGIN);
        }catch (Exception ex){
            if(installed != null && installed.exists())
                installed.delete();
            if(temp != null && temp.exists()) {
                try {
                    PluginUtils.rename(temp.getPath(),pluginEntity.getPath());
                } catch (IOException ignored) {
                }
            }
            throw new PluginException(pluginId, ex);
        }finally {
            enable(pluginId);
            load(pluginId);
        }
    }

    @Override
    public void disable(String pluginId) throws ExploreException {
        try {
            PluginEntity pluginEntity = pluginRepository.findById(pluginId).orElseThrow(PluginNotFoundException::new);
            Plugin plugin = PluginUtils.getPlugin(pluginEntity);
            plugin.disable();
            pluginEntity.setStatus(PluginStatus.DISABLED);
            pluginRepository.save(pluginEntity);
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.PLUGIN_DISABLED);
        }catch (Exception ex){
            throw new PluginException(pluginId, ex);
        }
    }

    @Override
    public void enable(String pluginId) throws ExploreException {
        try {
            PluginEntity pluginEntity = pluginRepository.findById(pluginId).orElseThrow(PluginNotFoundException::new);
            Plugin plugin = PluginUtils.getPlugin(pluginEntity);
            plugin.enable();
            pluginEntity.setStatus(PluginStatus.ENABLED);
            pluginRepository.save(pluginEntity);
            eventManager.event(Event.newEvent(pluginEntity),CoreEvents.PLUGIN_ENABLED);
        }catch (Exception ex){
            throw new PluginException(pluginId, ex);
        }
    }

    @Override
    public void load(String pluginId) throws ExploreException {
        try {
            PluginEntity pluginEntity = pluginRepository.findById(pluginId).orElseThrow(PluginNotFoundException::new);
            Plugin plugin = PluginUtils.getPlugin(pluginEntity);
            plugin.load();
        }catch (Exception ex){
            throw new PluginException(pluginId, ex);
        }
    }

    @Override
    public void unload(String pluginId) throws ExploreException {
        try {
            PluginEntity pluginEntity = pluginRepository.findById(pluginId).orElseThrow(PluginNotFoundException::new);
            Plugin plugin = PluginUtils.getPlugin(pluginEntity);
            plugin.unload();
        }catch (Exception ex){
            throw new PluginException(pluginId, ex);
        }
    }

    @Override
    public List<PluginEntity> installedPlugins() throws ExploreException {
        return pluginRepository.findByOrderByName();
    }

    @Override
    public List<PluginEntity> availablePlugins() throws ExploreException {
        try {
            List<String> availablePlugins = PluginUtils.availablePlugins(pluginsRepoPath);
            return availablePlugins.stream()
                    .map(PluginUtils::loadPlugin)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new PluginException("listing available plugins",e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        Policy.setPolicy(new PluginPolicy());
//        System.setSecurityManager(new SecurityManager());
    }

    @Override
    public void destroy() throws Exception {
        List<PluginEntity> activePlugins = pluginRepository.findByStatus(PluginStatus.ENABLED);
        activePlugins.forEach(pluginEntity -> {
            try {
                unload(pluginEntity.getId());
            } catch (ExploreException e) {
                e.printStackTrace();
            }
        });
    }
}
