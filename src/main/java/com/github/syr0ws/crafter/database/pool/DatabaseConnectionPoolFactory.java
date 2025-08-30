package com.github.syr0ws.crafter.database.pool;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.exception.UnsupportedDatabaseDriverException;

public interface DatabaseConnectionPoolFactory {

    DatabaseConnectionPool createConnectionPool(DatabaseConfig config) throws UnsupportedDatabaseDriverException;
}
