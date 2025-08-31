package com.github.syr0ws.crafter.database.pool;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.exception.InvalidDatabaseConfigException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a pool of reusable database connections.
 */
public interface DatabaseConnectionPool {

    /**
     * Opens the database connection pool.
     *
     * @throws SQLException if a database access error occurs
     */
    void open(DatabaseConfig config) throws InvalidDatabaseConfigException, SQLException;

    /**
     * Closes the database connection pool.
     *
     * @throws SQLException if a database access error occurs
     */
    void close() throws SQLException;

    /**
     * Checks whether the connection pool is closed.
     *
     * @return {@code true} if the pool is closed; {@code false} otherwise
     */
    boolean isClosed();

    /**
     * Obtains a connection from the pool.
     *
     * @return a {@link Connection} from the pool
     * @throws SQLException if a database access error occurs
     */
    Connection getConnection() throws SQLException;

    /**
     * Returns the underlying {@link DataSource} used by the pool.
     *
     * @return the data source
     */
    DataSource getDataSource();
}
