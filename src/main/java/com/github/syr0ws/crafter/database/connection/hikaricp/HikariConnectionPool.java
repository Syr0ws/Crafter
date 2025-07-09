package com.github.syr0ws.crafter.database.connection.hikaricp;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection pool based on HikariCP.
 */
public abstract class HikariConnectionPool implements DatabaseConnectionPool {

    private final DatabaseDriver driver;
    private final DatabaseConnectionConfig config;
    private HikariDataSource dataSource;

    public HikariConnectionPool(DatabaseDriver driver, DatabaseConnectionConfig config) {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(config, "config cannot be null");
        this.driver = driver;
        this.config = config;
    }

    protected abstract String getUrl(DatabaseDriver driver, DatabaseConnectionConfig config);

    protected HikariConfig getHikariConfig(DatabaseDriver driver, DatabaseConnectionConfig config) {

        String url = this.getUrl(driver, config);

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(this.driver.getDriverClass());
        hikariConfig.setUsername(this.config.username());
        hikariConfig.setPassword(this.config.password());
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setAutoCommit(true);

        return hikariConfig;
    }

    @Override
    public void open() throws SQLException {

        if (!this.isClosed()) {
            throw new SQLException("Connection pool already open");
        }

        HikariConfig hikariConfig = this.getHikariConfig(this.driver, this.config);
        this.dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public void close() throws SQLException {

        if (this.isClosed()) {
            throw new SQLException("Connection pool already closed");
        }

        this.dataSource.close();
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
