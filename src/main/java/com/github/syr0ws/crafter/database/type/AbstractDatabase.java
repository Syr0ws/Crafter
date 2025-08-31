package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabase implements Database {

    private final DatabaseConfig config;
    private final DatabaseConnectionPool pool;

    public AbstractDatabase(DatabaseConfig config, DatabaseConnectionPool pool) {
        Validate.notNull(pool, "pool cannot be null");
        this.config = config;
        this.pool = pool;
    }

    protected DatabaseConfig getConfig() {
        return this.config;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.pool.getConnection();
    }

    @Override
    public DatabaseConnectionPool getConnectionPool() {
        return this.pool;
    }
}
