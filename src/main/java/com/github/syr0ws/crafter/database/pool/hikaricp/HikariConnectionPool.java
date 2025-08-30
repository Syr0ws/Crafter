package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.exception.InvalidDatabaseConfigException;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * {@link DatabaseConnectionPool} implementation using HikariCP.
 */
public class HikariConnectionPool implements DatabaseConnectionPool {

    private final HikariConfigAdapter adapter;
    private HikariDataSource dataSource;

    public HikariConnectionPool(HikariConfigAdapter adapter) {
        Validate.notNull(adapter, "adapter cannot be null");
        this.adapter = adapter;
    }

    @Override
    public void open(DatabaseConfig config) throws InvalidDatabaseConfigException, SQLException {
        Validate.notNull(config, "config cannot be null");

        if (!this.isClosed()) {
            throw new SQLException("Connection pool already open");
        }

        HikariConfig hikariConfig = this.adapter.getHikariConfig(config);
        this.dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public void close() throws SQLException {

        if (this.isClosed()) {
            throw new SQLException("Connection pool already closed");
        }

        this.dataSource.close();
        this.dataSource = null;
    }

    @Override
    public boolean isClosed() {
        return this.dataSource == null || this.dataSource.isClosed();
    }

    @Override
    public Connection getConnection() throws SQLException {

        if (this.isClosed()) {
            throw new SQLException("Connection pool closed");
        }

        return this.dataSource.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
}
