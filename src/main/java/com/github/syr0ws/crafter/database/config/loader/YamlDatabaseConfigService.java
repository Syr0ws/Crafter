package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class YamlDatabaseConfigService implements DatabaseConfigService<FileConfiguration> {

    public static final String SECTION_DATABASE = "database";

    private final Map<String, DatabaseConfigLoader<FileConfiguration>> loaders = new HashMap<>();

    @Override
    public DatabaseConfig loadConnectionConfig(SupportedDriverList driverList, FileConfiguration config) throws ConfigurationException {
        Validate.notNull(driverList, "driverList cannot be null");
        Validate.notNull(config, "section cannot be null");

        DatabaseDriver driver = this.getDriver(config, driverList);

        DatabaseConfigLoader<FileConfiguration> loader = this.getConfigLoader(driver)
                .orElseThrow(() -> new ConfigurationException("Could not find configuration loader for driver '%s'".formatted(driver.getDriverName())));

        DatabaseConfig databaseConfig = loader.loadDatabaseConfig(config);
        databaseConfig.setDriver(driver);

        return databaseConfig;
    }

    @Override
    public DatabaseConfig loadConnectionConfig(DatabaseDriver driver, FileConfiguration config) throws ConfigurationException {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(config, "config cannot be null");

        DatabaseConfigLoader<FileConfiguration> loader = this.getConfigLoader(driver)
                .orElseThrow(() -> new ConfigurationException("Could not find configuration loader for driver '%s'".formatted(driver.getDriverName())));

        DatabaseConfig databaseConfig = loader.loadDatabaseConfig(config);
        databaseConfig.setDriver(driver);

        return databaseConfig;
    }

    @Override
    public void addConfigLoader(DatabaseDriver driver, DatabaseConfigLoader<FileConfiguration> loader) {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(loader, "loader cannot be null");
        this.loaders.put(driver.getDriverName(), loader);
    }

    @Override
    public void removeConfigLoader(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        this.loaders.remove(driver.getDriverName());
    }

    @Override
    public boolean hasConfigLoader(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        return this.loaders.containsKey(driver.getDriverName());
    }

    @Override
    public Optional<DatabaseConfigLoader<FileConfiguration>> getConfigLoader(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        return Optional.ofNullable(this.loaders.get(driver.getDriverName()));
    }

    private DatabaseDriver getDriver(FileConfiguration config, SupportedDriverList driverList) throws ConfigurationException {

        String path = SECTION_DATABASE + ".driver";
        String driverName = config.getString(path, "");

        return driverList.getDrivers().stream()
                .filter(driver -> driver.getDriverName().equalsIgnoreCase(driverName))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException(String.format("Database driver '%s' at '%s.driver' is invalid or unsupported", driverName, path)));
    }
}
