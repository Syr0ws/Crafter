package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;

public class SQLiteDatabase extends AbstractDatabase {

    public SQLiteDatabase(DatabaseConnectionPool pool, DatabaseConnectionConfig config) {
        super(pool, config);
    }

    @Override
    public void createDatabaseIfNotExists() {
        // do nothing as the database should be automatically created
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.SQLITE;
    }
}
