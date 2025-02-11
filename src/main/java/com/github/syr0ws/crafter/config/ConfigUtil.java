package com.github.syr0ws.crafter.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Utility class for manipulating configuration files.
 */
public class ConfigUtil {

    /**
     * Retrieves the {@link Material} associated with the given key from a configuration.
     *
     * @param section the configuration to retrieve the Material from.
     * @param key the key associated with the material value.
     * @return the {@link Material} corresponding to the key.
     * @throws IllegalArgumentException if the section or key is {@code null}.
     * @throws ConfigurationException if the material name is invalid or not found.
     */
    public static Material getMaterial(ConfigurationSection section, String key) throws ConfigurationException {

        if(section == null) {
            throw new IllegalArgumentException("section cannot be null");
        }

        if(key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        String materialName = section.getString(key, "");
        Material material = Material.matchMaterial(materialName);

        if(material == null) {
            String message = String.format("Invalid material name '%s' at '%s.%s' ", materialName, section.getCurrentPath(), key);
            throw new ConfigurationException(message);
        }

        return material;
    }
}
