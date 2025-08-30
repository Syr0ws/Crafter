package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class SQLiteConfigurationLoader implements DatabaseConfigLoader<FileConfiguration> {

    @Override
    public DatabaseConfig loadDatabaseConfig(FileConfiguration dataSource) {
        return new DatabaseConfig();
    }
}
