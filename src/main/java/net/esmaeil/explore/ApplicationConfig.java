package net.esmaeil.explore;

import com.google.common.io.Files;
import net.esmaeil.explore.language.LanguageManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;

@Configuration
public class ApplicationConfig {
    @Value("configs/valid_graphic_extensions.txt")
    private Resource validExtensionsResource;
    @Bean
    public Collection<String> getValidGraphicExtensions(){
        try {
            return new HashSet<>(Files.readLines(validExtensionsResource.getFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            return new HashSet<>();
        }
    }
}
