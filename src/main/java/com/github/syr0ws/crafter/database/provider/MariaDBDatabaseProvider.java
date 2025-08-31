package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.type.MariaDBDatabase;

/**
 * {@link DatabaseProvider} implementation for MariaDB databases.
 */
public class MariaDBDatabaseProvider implements DatabaseProvider {

    @Override
    public Database provide(DatabaseProviderContext context) {
        return new MariaDBDatabase(context.config(), context.pool());
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.MARIADB;
    }
}
