package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.type.PostgreSQLDatabase;

/**
 * {@link DatabaseProvider} implementation for PostgreSQL databases.
 */
public class PostgreSQLDatabaseProvider implements DatabaseProvider {

    @Override
    public Database provide(DatabaseProviderContext context) {
        return new PostgreSQLDatabase(context.config(), context.pool());
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.POSTGRESQL;
    }
}
