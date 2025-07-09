package com.github.syr0ws.crafter.database.connection;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;

/**
 * A factory interface for creating instances of {@link DatabaseConnectionPool}.
 */
public interface DatabaseConnectionPoolFactory {

    /**
     * Creates a {@link DatabaseConnectionPool} using the specified database driver
     * and connection configuration.
     *
     * @param driver the {@link DatabaseDriver} to use for the connection pool
     * @param config the {@link DatabaseConnectionConfig} containing connection settings
     * @return a new {@link DatabaseConnectionPool} instance
     */
    DatabaseConnectionPool createPool(DatabaseDriver driver, DatabaseConnectionConfig config);
}
