package com.github.syr0ws.crafter;

import com.github.syr0ws.crafter.database.DatabaseService;
import com.github.syr0ws.crafter.database.SimpleDatabaseService;
import com.github.syr0ws.crafter.database.config.loader.DatabaseConfigService;
import com.github.syr0ws.crafter.database.config.loader.YamlDatabaseConfigService;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;
import org.bukkit.configuration.file.FileConfiguration;

public class Crafter {

    public static DatabaseConfigService<FileConfiguration> createYamlDatabaseConfigService() {
        return new YamlDatabaseConfigService();
    }

    public static SupportedDriverList getDefaultSupportedDrivers() {

        SupportedDriverList list = new SupportedDriverList();
        list.addDrivers(DatabaseDriver.values());

        return list;
    }

    public static DatabaseService createDatabaseService() {
        return new SimpleDatabaseService();
    }
}
