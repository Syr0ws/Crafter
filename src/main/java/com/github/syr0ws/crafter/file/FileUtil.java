package com.github.syr0ws.crafter.file;

import org.bukkit.plugin.Plugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileUtil {

    /**
     * Copy a resource file from a plugin's resources to a specified target file.
     *
     * @param plugin       The plugin instance containing the resource. Must not be null.
     * @param resourcePath The relative path to the resource within the plugin's resources. Must not be null.
     * @param target       The target {@link Path} where the resource will be copied. Must not be null.
     * @param replace      If {@code true} and the target file already exists, it will be replaced by the resource file.
     *                     If {@code false} and the target file already exists, the method will do nothing.
     * @throws IOException              If an I/O error occurs during the copy process.
     * @throws IllegalArgumentException If any of the parameters {@code plugin}, {@code resourcePath}, or {@code target} is null.
     * @throws FileNotFoundException    If the specified resource cannot be found in the plugin's resources.
     */
    public static void copyResource(Plugin plugin, String resourcePath, Path target, boolean replace) throws IOException {

        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null");
        }

        if (resourcePath == null) {
            throw new IllegalArgumentException("resourcePath cannot be null");
        }

        if (target == null) {
            throw new IllegalArgumentException("target cannot be null");
        }

        if (Files.exists(target) && !replace) {
            return;
        }

        try (InputStream stream = plugin.getResource(resourcePath)) {

            if (stream == null) {
                throw new FileNotFoundException(String.format("Resource '%s' not found", resourcePath));
            }

            if(target.getParent() != null) {
                Files.createDirectories(target.getParent());
            }

            CopyOption[] options = replace ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{};
            Files.copy(stream, target, options);
        }
    }

    /**
     * Copy a resource file from a plugin's resources to the plugin's data folder by keeping the same relative resource path.
     *
     * @param plugin       The plugin instance containing the resource. Must not be null.
     * @param resourcePath The relative path to the resource within the plugin's resources. Must not be null.
     * @param replace      If {@code true} and the target file already exists, it will be replaced by the resource file.
     *                     If {@code false} and the target file already exists, the method will do nothing.
     * @throws IOException              If an I/O error occurs during the copy process.
     * @throws IllegalArgumentException If any of the parameters {@code plugin}, {@code resourcePath}, or {@code target} is null.
     * @throws FileNotFoundException    If the specified resource cannot be found in the plugin's resources.
     */
    public static void copyResource(Plugin plugin, String resourcePath, boolean replace) throws IOException {
        Path target = Paths.get(plugin.getDataFolder() + "/" + resourcePath);
        FileUtil.copyResource(plugin, resourcePath, target, replace);
    }
}
