package com.github.syr0ws.crafter.database;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.migration.DatabaseMigrator;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPoolFactory;

public interface DatabaseService {

    Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory factory) throws Exception;

    Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory poolFactory, DatabaseMigrator migrator) throws Exception;
}
