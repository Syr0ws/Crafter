package com.github.syr0ws.crafter.database;

import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a general interface for interacting with a database.
 */
public interface Database {

    /**
     * Creates the database if it does not already exist.
     *
     * @throws Exception if an error occurs during database creation
     */
    void createDatabaseIfNotExists() throws Exception;

    /**
     * Returns the database connection pool associated with this database.
     *
     * @return the {@link DatabaseConnectionPool} instance
     */
    DatabaseConnectionPool getConnectionPool();

    /**
     * Obtains a connection to the database.
     *
     * @return a {@link Connection} to the database
     * @throws SQLException if a database access error occurs
     */
    Connection getConnection() throws SQLException;

    /**
     * Returns the database driver used by this database.
     *
     * @return the {@link DatabaseDriver}
     */
    DatabaseDriver getDriver();
}
