package com.github.syr0ws.crafter.config;

import com.github.syr0ws.crafter.util.Direction;
import com.github.syr0ws.crafter.util.Validate;
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
        Validate.notNull(section, "section cannot be null");
        Validate.notEmpty(key, "key cannot be null or empty");

        String materialName = section.getString(key, "");
        Material material = Material.matchMaterial(materialName);

        if(material == null) {
            String message = String.format("Invalid material name '%s' at '%s.%s' ", materialName, section.getCurrentPath(), key);
            throw new ConfigurationException(message);
        }

        return material;
    }

    /**
     * Retrieves the symbol associated with a given {@link Direction} from the provided configuration.
     *
     * @param direction the direction for which to retrieve the symbol.
     * @param section   the configuration section containing the direction symbols.
     * @return the symbol associated with the given direction, or an empty string if not found.
     * @throws IllegalArgumentException if {@code direction} or {@code section} is null.
     */
    public static String getDirectionSymbol(Direction direction, ConfigurationSection section) {
        Validate.notNull(direction, "direction cannot be null");
        Validate.notNull(section, "section cannot be null");

        String directionKey = direction.name().toLowerCase().replace("_", "-");

        return section.getString(directionKey, "");
    }
}
