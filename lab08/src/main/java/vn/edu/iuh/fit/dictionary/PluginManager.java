package vn.edu.iuh.fit.dictionary;

import com.example.language.Plugin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {

    //registry
    public static final Map<Class<?>, Plugin> PLUGINS = new HashMap<>();

    public static void loadPlugin(String jarFilePath) {

        try(JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            URL[] urls = new URL[]{new URL("jar:file:" + jarFilePath + "!/")};

            try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {
                while (jarEntries.hasMoreElements()) {
                    JarEntry entry = jarEntries.nextElement();
                    if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                        continue;
                    }
                    // -6 because of .class
                    String className = entry.getName().substring(0, entry.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class<?> clazz = classLoader.loadClass(className);
                    if (clazz.isInterface()) {
                     continue;
                    }
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> interfaceClass : interfaces) {
                        if (!Plugin.class.isAssignableFrom(interfaceClass)) {
                            continue;
                        }
                        Constructor<?> constructor = clazz.getConstructor();
                        Plugin instance = (Plugin) constructor.newInstance();
                        System.out.println("Loading plugin " + instance.name());
                        PLUGINS.put(interfaceClass, instance);
                    }
                }
            } catch (NoSuchMethodException | InstantiationException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void loadDirJarFile(String dirPath)throws Exception{
        File directory = new File(dirPath);
        if(directory.isDirectory()){
            for (File files: Objects.requireNonNull(directory.listFiles())){
                if(files.isFile() && files.getName().toLowerCase().endsWith("jar")){
                    loadDirJarFile(files.getAbsolutePath());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Plugin> T get(Class<T> clazz) {
        return (T) PLUGINS.get(clazz);
    }
}