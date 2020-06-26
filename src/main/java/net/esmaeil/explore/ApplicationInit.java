package net.esmaeil.explore;

import net.esmaeil.explore.graphic.GraphicManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class ApplicationInit implements InitializingBean {
    private final GraphicManager graphicManager;
    @Value("graphics/graphics")
    private Resource graphicsResource;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public ApplicationInit(GraphicManager graphicManager) {
        this.graphicManager = graphicManager;
    }

    private void graphicInit(){
        try {
            graphicManager.addGraphicFolder(CoreConstants.CORE_PLUGIN_ID,graphicsResource.getFile().getParentFile());
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.graphicInit();
    }
}
