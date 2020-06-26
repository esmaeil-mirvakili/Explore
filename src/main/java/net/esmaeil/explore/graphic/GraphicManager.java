package net.esmaeil.explore.graphic;

import net.esmaeil.explore.ui.utils.Size;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public interface GraphicManager {
    Image getGraphic(String pluginId,String name);
    Map<String,Image> getGraphics(String pluginId);
    Image getGraphic(String pluginId, String name, Size size);
    Map<String,Image> getGraphics(String pluginId, Size size);
    Image removeGraphic(String pluginId,String name);
    Map<String,Image> removeGraphics(String pluginId);
    void addGraphic(String pluginId, String name, File graphic);
    void addGraphicFolder(String pluginId, File folder);
}
