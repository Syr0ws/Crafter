package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;

public record DatabaseProviderContext(DatabaseConnectionPool pool, DatabaseConfig config) {

}
