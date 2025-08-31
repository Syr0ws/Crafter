package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;

/**
 * Context for providing a {@link Database}, containing the connection pool and database configuration.
 *
 * @param pool   database connection pool
 * @param config database configuration
 */
public record DatabaseProviderContext(DatabaseConnectionPool pool, DatabaseConfig config) {

}
