package com.github.syr0ws.crafter.database.pool;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;

/**
 * Factory for creating {@link DatabaseConnectionPool} instances.
 */
public interface DatabaseConnectionPoolFactory {

    /**
     * Creates a {@link DatabaseConnectionPool} for the given {@link DatabaseConfig}.
     *
     * @param config database configuration
     * @return a new database connection pool
     */
    DatabaseConnectionPool createConnectionPool(DatabaseConfig config);
}
