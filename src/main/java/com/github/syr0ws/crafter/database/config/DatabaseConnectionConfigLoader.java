package com.github.syr0ws.crafter.database.config;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

/**
 * Loads {@link DatabaseConnectionConfig} and {@link DatabaseDriver} values from
 * a plugin's configuration file.
 */
public class DatabaseConnectionConfigLoader {

    private final Plugin plugin;

    /**
     * Constructs a new {@code DatabaseConnectionConfigLoader} with the given plugin context.
     *
     * @param plugin the plugin providing access to the configuration file
     * @throws IllegalArgumentException if {@code plugin} is {@code null}
     */
    public DatabaseConnectionConfigLoader(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
    }

    /**
     * Loads the {@link DatabaseConnectionConfig} from the {@code database} section of the plugin's configuration.
     *
     * @return a {@link DatabaseConnectionConfig} constructed from the configuration
     * @throws ConfigurationException if required fields are missing or invalid
     */
    public DatabaseConnectionConfig loadConfig() throws ConfigurationException {
        ConfigurationSection section = this.getDatabaseSection();

        String host = section.getString("host", "localhost");
        int port = section.getInt("port", -1);

        String database = section.getString("database");
        String username = section.getString("username", "root");
        String password = section.getString("password", "");

        if (database == null) {
            throw new ConfigurationException("Invalid property '%s.database': null".formatted(section.getCurrentPath()));
        }

        if (port < 0) {
            throw new ConfigurationException("Invalid property '%s.port': %d".formatted(section.getCurrentPath(), port));
        }

        return new DatabaseConnectionConfig(host, port, database, username, password);
    }

    /**
     * Loads the {@link DatabaseDriver} from the plugin's configuration.
     *
     * @return the corresponding {@link DatabaseDriver}
     * @throws ConfigurationException if the driver value is missing or invalid
     */
    public DatabaseDriver loadDriver() throws ConfigurationException {
        ConfigurationSection section = this.getDatabaseSection();

        String driverName = section.getString("driver", "");

        return Arrays.stream(DatabaseDriver.values())
                .filter(driver -> driver.getDriverName().equalsIgnoreCase(driverName))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException(String.format("Property '%s' at '%s.driver' is invalid", driverName, section.getCurrentPath())));
    }

    private ConfigurationSection getDatabaseSection() {

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("database");
        Validate.notNull(section, "Section 'database' not found");

        return section;
    }
}
