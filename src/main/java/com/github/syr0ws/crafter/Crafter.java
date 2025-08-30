package com.github.syr0ws.crafter;

import com.github.syr0ws.crafter.database.DatabaseService;
import com.github.syr0ws.crafter.database.SimpleDatabaseService;
import com.github.syr0ws.crafter.database.config.loader.DatabaseConfigService;
import com.github.syr0ws.crafter.database.config.loader.YamlDatabaseConfigService;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Global class with default behaviors for the Crafter library.
 */
public class Crafter {

    /**
     * Creates a {@link DatabaseConfigService} instance for {@link FileConfiguration}.
     *
     * @return a {@link DatabaseConfigService} that loads database configurations from YAML files
     */
    public static DatabaseConfigService<FileConfiguration> createYamlDatabaseConfigService() {
        return new YamlDatabaseConfigService();
    }

    /**
     * Creates a {@link SupportedDriverList} containing all default {@link DatabaseDriver} values.
     *
     * @return list of default supported database drivers
     */
    public static SupportedDriverList getDefaultSupportedDrivers() {

        SupportedDriverList list = new SupportedDriverList();
        list.addDrivers(DatabaseDriver.values());

        return list;
    }

    /**
     * Creates a {@link DatabaseService} instance.
     *
     * @return a database service
     */
    public static DatabaseService createDatabaseService() {
        return new SimpleDatabaseService();
    }
}
