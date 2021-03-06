package me.nubdotdev.celestia.addon;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public abstract class ExtensionManager<T extends Extension> {

    private final File file;
    private final Class<T> extensionType;
    private final Set<T> extensions;

    protected ExtensionManager(File file, Class<T> extensionType) {
        this.file = file;
        this.extensionType = extensionType;
        this.extensions = new HashSet<>();
        registerExtensions();
    }

    @SuppressWarnings("unchecked")
    public void registerExtension(File file) {
        if (file == null)
            return;
        try {
            JarInputStream jarStream = new JarInputStream(new FileInputStream(file));
            JarEntry entry;
            while (true) {
                entry = jarStream.getNextJarEntry();
                if (entry == null)  {
                    Bukkit.getLogger().warning("Failed to register extension '" + file.getName() + "'");
                    break;
                }
                String fileName = entry.getName();
                if (fileName.endsWith(".class")) {
                    String className = fileName
                            .replaceAll("/", ".")
                            .substring(0, fileName.length() - 6);
                    URLClassLoader registerer = new URLClassLoader(
                            new URL[] { new URL("jar:file:" + file.getPath() + "!/") },
                            ExtensionManager.class.getClassLoader()
                    );
                    Class<?> clazz = Class.forName(className, false, registerer);
                    if (extensionType.isAssignableFrom(clazz)) {
                        T extension = ((Class<? extends T>) clazz).newInstance();
                        extension.setPath(file.getPath());
                        extensions.add(extension);
                        Bukkit.getLogger().info("Successfully registered extension '" + extension.getExtensionName() + "'");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerExtension(String path) {
        registerExtension(new File(path));
    }

    public void registerExtensions() {
        for (File f : Objects.requireNonNull(file.listFiles()))
            registerExtension(f);
    }

    public void unregisterExtension(T extension) {
        extensions.remove(extension);
        Bukkit.getLogger().info("Successfully unregistered extension '" + extension.getExtensionName() + "'");
    }
    
    public void unregisterExtensions() {
        for (T extension : extensions)
            unregisterExtension(extension);
    }

    public void reloadExtension(T extension) {
        unregisterExtension(extension);
        registerExtension(extension.getPath());
    }
    
    public void reloadExtensions() {
        unregisterExtensions();
        registerExtensions();
    }

    public Set<T> getExtensions() {
        return extensions;
    }

}
