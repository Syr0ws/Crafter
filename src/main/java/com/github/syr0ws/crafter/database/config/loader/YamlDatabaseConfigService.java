package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Implementation of {@link DatabaseConfigService} that loads {@link DatabaseConfig}
 * from a {@link FileConfiguration}.
 */
public class YamlDatabaseConfigService implements DatabaseConfigService<FileConfiguration> {

    public static final String SECTION_DATABASE = "database";

    @Override
    public DatabaseConfig loadConfig(SupportedDriverList driverList, FileConfiguration config) throws ConfigurationException {
        Validate.notNull(driverList, "driverList cannot be null");
        Validate.notNull(config, "section cannot be null");

        DatabaseDriver driver = this.getDriver(config, driverList);

        DatabaseConfigLoader<FileConfiguration> loader = this.getConfigLoader(driver);

        DatabaseConfig databaseConfig = loader.loadDatabaseConfig(config);
        databaseConfig.setDriver(driver);

        return databaseConfig;
    }

    @Override
    public DatabaseConfig loadConfig(DatabaseDriver driver, FileConfiguration config) throws ConfigurationException {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(config, "config cannot be null");

        DatabaseConfigLoader<FileConfiguration> loader = this.getConfigLoader(driver);

        DatabaseConfig databaseConfig = loader.loadDatabaseConfig(config);
        databaseConfig.setDriver(driver);

        return databaseConfig;
    }

    /**
     * Returns the appropriate {@link DatabaseConfigLoader} for the given driver.
     *
     * @param driver database driver
     * @return the configuration loader for the given driver
     */
    private DatabaseConfigLoader<FileConfiguration> getConfigLoader(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        return driver == DatabaseDriver.SQLITE ? new SQLiteConfigurationLoader() : new RemoteDatabaseConfigLoader();
    }

    /**
     * Extracts the {@link DatabaseDriver} from the configuration using the supported driver list.
     *
     * @param config     YAML configuration file
     * @param driverList supported driver list
     * @return the resolved database driver
     * @throws ConfigurationException if the driver is invalid or unsupported
     */
    private DatabaseDriver getDriver(FileConfiguration config, SupportedDriverList driverList) throws ConfigurationException {

        String path = SECTION_DATABASE + ".driver";
        String driverName = config.getString(path, "");

        return driverList.getDrivers().stream()
                .filter(driver -> driver.getDriverName().equalsIgnoreCase(driverName))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException(String.format("Database driver '%s' at '%s' is invalid or unsupported", driverName, path)));
    }
}
