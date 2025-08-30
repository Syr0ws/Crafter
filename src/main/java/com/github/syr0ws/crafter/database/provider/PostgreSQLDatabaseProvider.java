package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.driver.CommonDatabaseDriver;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.type.PostgreSQLDatabase;

public class PostgreSQLDatabaseProvider implements DatabaseProvider {

    @Override
    public Database provide(DatabaseProviderContext context) {
        return new PostgreSQLDatabase(context.config(), context.pool());
    }

    @Override
    public DatabaseDriver getDriver() {
        return CommonDatabaseDriver.POSTGRESQL;
    }
}
