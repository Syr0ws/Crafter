package com.github.syr0ws.crafter;

import com.github.syr0ws.crafter.database.DatabaseService;
import com.github.syr0ws.crafter.database.config.loader.DatabaseConfigService;
import com.github.syr0ws.crafter.database.config.loader.RemoteDatabaseConfigLoader;
import com.github.syr0ws.crafter.database.config.loader.SQLiteConfigurationLoader;
import com.github.syr0ws.crafter.database.config.loader.YamlDatabaseConfigService;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;
import com.github.syr0ws.crafter.database.provider.MariaDBDatabaseProvider;
import com.github.syr0ws.crafter.database.provider.MySQLDatabaseProvider;
import com.github.syr0ws.crafter.database.provider.PostgreSQLDatabaseProvider;
import com.github.syr0ws.crafter.database.provider.SQLiteDatabaseProvider;
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

        DatabaseService service = new DatabaseService();
        service.addProvider(new SQLiteDatabaseProvider());
        service.addProvider(new MySQLDatabaseProvider());
        service.addProvider(new MariaDBDatabaseProvider());
        service.addProvider(new PostgreSQLDatabaseProvider());

        return service;
    }
}
