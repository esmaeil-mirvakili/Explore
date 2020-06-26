package net.esmaeil.explore.plugin;

import com.google.common.io.Files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class PluginUtils {
    public static PluginEntity loadPlugin(String pluginPath){
        return null;
    }

    public static String findPluginRootClass(ClassLoader classLoader,String basePackage){
        return null;
    }

    public static boolean isValid(String pluginPath){
        return true;
    }

    public static Plugin getPlugin(PluginEntity pluginEntity) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        File file = new File(pluginEntity.getPath());
        ClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
        return (Plugin) classLoader.loadClass(Objects.requireNonNull(pluginEntity).getRootClass()).getDeclaredConstructor(Plugin.class).newInstance();
    }

    public static List<String> availablePlugins(String path) throws Exception{
        File file = new File(path);
        return Arrays.stream(Objects.requireNonNull(file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                String lower = name.toLowerCase();
                return lower.endsWith(".jar") || lower.endsWith(".zip");
            }
        })))
                .map(File::getPath)
                .filter(PluginUtils::isValid)
                .collect(Collectors.toList());

    }

    public static File copy(String filePath,String to) throws IOException {
        File source = new File(filePath);
        File destination = new File(new File(to),source.getName());
        Files.copy(source,destination);
        return destination;
    }

    public static File rename(String filePath,String to) throws IOException {
        File source = new File(filePath);
        File destination = new File(source.getParentFile(),to);
        Files.move(source,destination);
        return destination;
    }
}
