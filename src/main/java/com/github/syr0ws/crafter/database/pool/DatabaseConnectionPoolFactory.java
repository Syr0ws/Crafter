package com.github.syr0ws.crafter.database.pool;

import com.github.syr0ws.crafter.database.exception.UnsupportedDatabaseDriverException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;

public interface DatabaseConnectionPoolFactory {

    DatabaseConnectionPool createConnectionPool(DatabaseConfig config) throws UnsupportedDatabaseDriverException;
}
