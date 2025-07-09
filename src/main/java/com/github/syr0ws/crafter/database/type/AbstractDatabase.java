package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabase implements Database {

    private final DatabaseConnectionPool pool;
    private final DatabaseConnectionConfig config;

    public AbstractDatabase(DatabaseConnectionPool pool, DatabaseConnectionConfig config) {
        Validate.notNull(pool, "pool cannot be null");
        Validate.notNull(config, "config cannot be null");

        this.pool = pool;
        this.config = config;
    }

    @Override
    public DatabaseConnectionPool getConnectionPool() {
        return this.pool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.pool.getConnection();
    }

    protected DatabaseConnectionConfig getConfig() {
        return this.config;
    }
}
