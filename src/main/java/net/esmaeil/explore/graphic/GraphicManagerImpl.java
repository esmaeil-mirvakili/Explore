package net.esmaeil.explore.graphic;

import com.google.common.io.Files;
import net.esmaeil.explore.ui.utils.Size;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GraphicManagerImpl implements GraphicManager {
    private final GraphicEntityRepository graphicEntityRepository;
    private final Collection<String> validGraphicExtensions;

    @Autowired
    public GraphicManagerImpl(GraphicEntityRepository graphicEntityRepository, Collection<String> validGraphicExtensions) {
        this.graphicEntityRepository = graphicEntityRepository;
        this.validGraphicExtensions = validGraphicExtensions;
    }

    @Override
    public Image getGraphic(String pluginId, String name, Size size) {
        AtomicReference<Image> image = new AtomicReference<>(null);
        Optional<GraphicEntity> graphicEntityOptional = graphicEntityRepository.findByPluginIdAndName(pluginId, name);
        graphicEntityOptional.ifPresent(graphicEntity -> {
            image.set(createImage(graphicEntity, size));
        });
        return image.get();
    }

    @Override
    public Map<String, Image> getGraphics(String pluginId, Size size) {
        Map<String, Image> imageMap = new HashMap<>();
        List<GraphicEntity> graphicEntities = graphicEntityRepository.findByPluginId(pluginId);
        graphicEntities.forEach(graphicEntity -> imageMap.put(graphicEntity.getName(), createImage(graphicEntity, size)));
        return imageMap;
    }

    @Override
    public Image getGraphic(String pluginId, String name) {
        return getGraphic(pluginId, name, null);
    }

    @Override
    public Map<String, Image> getGraphics(String pluginId) {
        return getGraphics(pluginId, null);
    }

    @Override
    public Image removeGraphic(String pluginId, String name) {
        Image image = getGraphic(pluginId, name);
        graphicEntityRepository.deleteByPluginIdAndName(pluginId, name);
        return image;
    }

    @Override
    public Map<String, Image> removeGraphics(String pluginId) {
        Map<String, Image> imageMap = getGraphics(pluginId);
        graphicEntityRepository.deleteByPluginId(pluginId);
        return imageMap;
    }

    @Override
    public void addGraphic(String pluginId, String name, File graphic) {
        if (!graphic.exists())
            return;
        GraphicEntity graphicEntity = new GraphicEntity();
        graphicEntity.setPluginId(pluginId);
        graphicEntity.setName(name);
        graphicEntity.setPath(graphic.getAbsolutePath());
        graphicEntityRepository.save(graphicEntity);
    }

    @Override
    public void addGraphicFolder(String pluginId, File folder) {
        List<File> files = extractChildren(folder);
        files.forEach(file -> {
            String name = Files.getNameWithoutExtension(file.getName());
            addGraphic(pluginId, name, file);
        });
    }

    private Image createImage(GraphicEntity graphicEntity, Size size) {
        try {
            if (size == null)
                return new Image(new FileInputStream(graphicEntity.getPath()));
            return new Image(new FileInputStream(graphicEntity.getPath()), size.getWidth(), size.getHeight(), false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<File> extractChildren(File parent) {
        List<File> files = new LinkedList<>();
        File[] children = parent.listFiles(child ->
                child.isDirectory() || validGraphicExtensions.contains(Files.getFileExtension(child.getName()))
        );
        if (children != null)
            for (File child : children) {
                if (child.isDirectory())
                    files.addAll(extractChildren(child));
                else
                    files.add(child);
            }
        return files;
    }
}
