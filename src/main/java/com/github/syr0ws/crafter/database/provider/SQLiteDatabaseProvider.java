package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.type.SQLiteDatabase;

/**
 * {@link DatabaseProvider} implementation for SQLite databases.
 */
public class SQLiteDatabaseProvider implements DatabaseProvider {

    @Override
    public Database provide(DatabaseProviderContext context) {
        return new SQLiteDatabase(context.config(), context.pool());
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.SQLITE;
    }
}
