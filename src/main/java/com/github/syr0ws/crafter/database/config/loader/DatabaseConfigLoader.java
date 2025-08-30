package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;

/**
 * Loads a {@link DatabaseConfig} from a given data source.
 *
 * @param <T> type of the data source
 */
public interface DatabaseConfigLoader<T> {

    /**
     * Loads a {@link DatabaseConfig} from the specified data source.
     *
     * @param dataSource source used to load the configuration
     * @return the loaded database configuration
     * @throws ConfigurationException if the configuration cannot be loaded
     */
    DatabaseConfig loadDatabaseConfig(T dataSource) throws ConfigurationException;
}
