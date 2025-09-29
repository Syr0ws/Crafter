package com.github.syr0ws.crafter.config;

import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a configuration map that provides type-safe access to properties.
 */
public class ConfigurationMap {

    private final ConfigurationSection parent;
    private final Map<?,?> properties;

    /**
     * Constructs a new {@link ConfigurationMap} with the specified properties.
     *
     * @param parent the parent configuration section
     * @param properties the properties map to wrap
     */
    public ConfigurationMap(ConfigurationSection parent, Map<?, ?> properties) {
        Validate.notNull(parent, "parent cannot be null");
        Validate.notNull(properties, "properties cannot be null");

        this.parent = parent;
        this.properties = Map.copyOf(properties);
    }

    /**
     * Returns the {@code String} value associated with the specified key or {@code null} if not present.
     *
     * @param key the property key
     * @return the {@code String} value or {@code null}
     */
    public String getString(String key) {
        return this.get(key, Object.class).map(Object::toString).orElse(null);
    }

    /**
     * Returns the {@code String} value associated with the specified key or the given default value if not present.
     *
     * @param key the property key
     * @param defaultValue the default value to return if the key is not present
     * @return the {@code String} value or {@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        return this.get(key, Object.class).map(Object::toString).orElse(defaultValue);
    }

    /**
     * Checks if the value associated with the specified key is a {@code String}.
     *
     * @param key the property key
     * @return {@code true} if the value is a {@code String}, otherwise {@code false}
     */
    public boolean isString(String key) {
        return this.get(key, String.class).isPresent();
    }

    /**
     * Returns the {@code int} value associated with the specified key or {@code 0} if not present.
     *
     * @param key the property key
     * @return the {@code int} value or {@code 0}
     */
    public int getInt(String key) {
        return this.get(key, Number.class).map(Number::intValue).orElse(0);
    }

    /**
     * Returns the {@code int} value associated with the specified key or the given default value if not present.
     *
     * @param key the property key
     * @param defaultValue the default value to return if the key is not present
     * @return the {@code int} value or {@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        return this.get(key, Number.class).map(Number::intValue).orElse(defaultValue);
    }

    /**
     * Checks if the value associated with the specified key is an {@code Integer}.
     *
     * @param key the property key
     * @return {@code true} if the value is an {@code Integer}, otherwise {@code false}
     */
    public boolean isInt(String key) {
        return this.get(key, Integer.class).isPresent();
    }

    /**
     * Returns the {@code double} value associated with the specified key or {@code 0.0} if not present.
     *
     * @param key the property key
     * @return the {@code double} value or {@code 0.0}
     */
    public double getDouble(String key) {
        return this.get(key, Number.class).map(Number::doubleValue).orElse(0.0);
    }

    /**
     * Returns the {@code double} value associated with the specified key or the given default value if not present.
     *
     * @param key the property key
     * @param defaultValue the default value to return if the key is not present
     * @return the {@code double} value or {@code defaultValue}
     */
    public double getDouble(String key, double defaultValue) {
        return this.get(key, Number.class).map(Number::doubleValue).orElse(defaultValue);
    }

    /**
     * Checks if the value associated with the specified key is a {@code Double}.
     *
     * @param key the property key
     * @return {@code true} if the value is a {@code Double}, otherwise {@code false}
     */
    public boolean isDouble(String key) {
        return this.get(key, Double.class).isPresent();
    }

    /**
     * Returns the {@code boolean} value associated with the specified key or {@code false} if not present.
     *
     * @param key the property key
     * @return the {@code boolean} value or {@code false}
     */
    public boolean getBoolean(String key) {
        return this.get(key, Boolean.class).orElse(false);
    }

    /**
     * Returns the {@code boolean} value associated with the specified key or the given default value if not present.
     *
     * @param key the property key
     * @param defaultValue the default value to return if the key is not present
     * @return the {@code boolean} value or {@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return this.get(key, Boolean.class).orElse(defaultValue);
    }

    /**
     * Checks if the value associated with the specified key is a {@code Boolean}.
     *
     * @param key the property key
     * @return {@code true} if the value is a {@code Boolean}, otherwise {@code false}
     */
    public boolean isBoolean(String key) {
        return this.get(key, Boolean.class).isPresent();
    }

    /**
     * Checks if the value associated with the specified key is a {@code List}.
     *
     * @param key the property key
     * @return {@code true} if the value is a {@code List}, otherwise {@code false}
     */
    public boolean isList(String key) {
        return this.get(key, List.class).isPresent();
    }

    /**
     * Returns the {@code List<String>} value associated with the specified key or an empty list if not present.
     *
     * @param key the property key
     * @return the {@code List<String>} value or an empty list
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        return (List<String>) this.get(key, List.class).orElse(new ArrayList<>());
    }

    /**
     * Returns the {@code List<String>} value associated with the specified key or the given default value if not present.
     *
     * @param key the property key
     * @param defaultValue the default value to return if the key is not present
     * @return the {@code List<String>} value or {@code defaultValue}
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key, List<String> defaultValue) {
        return (List<String>) this.get(key, List.class).orElse(defaultValue);
    }

    /**
     * Returns the {@code ConfigurationMap} associated with the specified key or {@code null} if not present or not a map.
     *
     * @param key the property key
     * @return the {@code ConfigurationMap} or {@code null}
     */
    public ConfigurationMap getConfigurationMap(String key) {

        if(!isConfigurationMap(key)) {
            return null;
        }

        Map<?,?> properties = (Map<?, ?>) this.properties.get(key);

        return new ConfigurationMap(this.parent, properties);
    }

    /**
     * Checks if the value associated with the specified key can be retrieved as a {@link ConfigurationMap}.
     *
     * @param key the property key
     * @return {@code true} if the value is a map, otherwise {@code false}
     */
    public boolean isConfigurationMap(String key) {

        Object object = this.properties.get(key);

        if(object == null) {
            return false;
        }

        return object instanceof Map<?,?>;
    }

    /**
     * Checks if the specified key is present in the properties map.
     *
     * @param key the property key
     * @return {@code true} if the key is present, otherwise {@code false}
     */
    public boolean isSet(String key) {
        Validate.notEmpty(key, "key cannot be null or empty");
        return this.properties.containsKey(key);
    }

    /**
     * Returns the parent {@link ConfigurationSection} of this map.
     *
     * @return the parent {@link ConfigurationSection}
     */
    public ConfigurationSection getParent() {
        return this.parent;
    }

    /**
     * Returns the current path of this map.
     *
     * @return the current path as a {@code String}
     */
    public String getCurrentPath() {
        return this.parent.getCurrentPath();
    }

    /**
     * Returns the value associated with the specified key as an {@code Optional} of the given type.
     *
     * @param key the property key
     * @param type the expected type of the value
     * @param <T> the type of the value
     * @return an {@code Optional} containing the value if present and of the correct type, otherwise empty
     */
    private <T> Optional<T> get(String key, Class<T> type) {
        Validate.notEmpty(key, "key cannot be null or empty");
        Validate.notNull(type, "type cannot be null");

        if(!this.properties.containsKey(key)) {
            return Optional.empty();
        }

        Object object = this.properties.get(key);

        if(object == null || !type.isAssignableFrom(object.getClass())) {
            return Optional.empty();
        }

        T value = type.cast(object);

        return Optional.of(value);
    }
}
