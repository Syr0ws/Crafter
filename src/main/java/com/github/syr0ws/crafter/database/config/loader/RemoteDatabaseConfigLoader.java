package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class RemoteDatabaseConfigLoader implements DatabaseConfigLoader<FileConfiguration> {

    private static final String PROPERTY_HOST = "host";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_DATABASE = "database";
    private static final String PROPERTY_USER = "username";
    private static final String PROPERTY_PASSWORD = "password";

    @Override
    public DatabaseConfig loadDatabaseConfig(FileConfiguration config) throws ConfigurationException {

        ConfigurationSection section = config.getConfigurationSection(YamlDatabaseConfigService.SECTION_DATABASE);

        if (section == null) {
            throw new ConfigurationException("Section '%s' not found in configuration".formatted(YamlDatabaseConfigService.SECTION_DATABASE));
        }

        // Loading properties.
        if (!section.isString(PROPERTY_HOST)) {
            throw new ConfigurationException("Property '%s' not found or is not a string at '%s'".formatted(PROPERTY_HOST, section.getCurrentPath()));
        }

        if (!section.isInt(PROPERTY_PORT)) {
            throw new ConfigurationException("Property '%s' not found or is not a string at '%s'".formatted(PROPERTY_PORT, section.getCurrentPath()));
        }

        if (!section.isString(PROPERTY_DATABASE)) {
            throw new ConfigurationException("Property '%s' not found or is not a string at '%s'".formatted(PROPERTY_DATABASE, section.getCurrentPath()));
        }

        if (!section.isString(PROPERTY_USER)) {
            throw new ConfigurationException("Property '%s' not found or is not a string at '%s'".formatted(PROPERTY_USER, section.getCurrentPath()));
        }

        if (!section.isString(PROPERTY_PASSWORD)) {
            throw new ConfigurationException("Property '%s' not found or is not a string at '%s'".formatted(PROPERTY_PASSWORD, section.getCurrentPath()));
        }

        String host = section.getString(PROPERTY_HOST);
        int port = section.getInt(PROPERTY_PORT);

        if (port <= 0) {
            throw new ConfigurationException("Property '%s' is invalid at '%s'".formatted(PROPERTY_PORT, section.getCurrentPath()));
        }

        String database = section.getString(PROPERTY_DATABASE);
        String username = section.getString(PROPERTY_USER);
        String password = section.getString(PROPERTY_PASSWORD);

        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setHost(host);
        databaseConfig.setPort(port);
        databaseConfig.setDatabase(database);
        databaseConfig.setUser(username);
        databaseConfig.setPassword(password);

        return databaseConfig;
    }
}
