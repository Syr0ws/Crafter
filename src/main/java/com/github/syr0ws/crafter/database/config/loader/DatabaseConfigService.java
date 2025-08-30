package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;

/**
 * Provides methods to load {@link DatabaseConfig} instances from data sources.
 *
 * @param <T> type of the data source
 */
public interface DatabaseConfigService<T> {

    /**
     * Loads a {@link DatabaseConfig} using the given supported driver list and data source.
     *
     * @param driverList supported driver list
     * @param dataSource source used to load the configuration
     * @return the loaded database configuration
     * @throws ConfigurationException if the configuration cannot be loaded or if the driver is unsupported
     */
    DatabaseConfig loadConfig(SupportedDriverList driverList, T dataSource) throws ConfigurationException;

    /**
     * Loads a {@link DatabaseConfig} using the given database driver and data source.
     *
     * @param driver database driver
     * @param dataSource source used to load the configuration
     * @return the loaded database configuration
     * @throws ConfigurationException if the configuration cannot be loaded
     */
    DatabaseConfig loadConfig(DatabaseDriver driver, T dataSource) throws ConfigurationException;
}
