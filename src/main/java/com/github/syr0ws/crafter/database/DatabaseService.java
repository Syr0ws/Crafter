package com.github.syr0ws.crafter.database;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.migration.DatabaseMigrator;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPoolFactory;

/**
 * Provides methods to set up a {@link Database} instance.
 */
public interface DatabaseService {

    /**
     * Sets up a {@link Database} using the given configuration and connection pool factory.
     *
     * @param config  database configuration
     * @param factory connection pool factory
     * @return the configured database
     * @throws Exception if the setup fails
     */
    Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory factory) throws Exception;

    /**
     * Sets up a {@link Database} using the given configuration, connection pool factory, and migrator.
     *
     * @param config      database configuration
     * @param poolFactory connection pool factory
     * @param migrator    database migrator
     * @return the configured database
     * @throws Exception if the setup fails
     */
    Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory poolFactory, DatabaseMigrator migrator) throws Exception;
}
