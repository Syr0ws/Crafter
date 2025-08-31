package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;

public class SQLiteDatabase extends AbstractDatabase {

    public SQLiteDatabase(DatabaseConfig config, DatabaseConnectionPool pool) {
        super(config, pool);
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
