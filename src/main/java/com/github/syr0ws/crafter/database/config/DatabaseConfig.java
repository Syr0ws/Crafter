package com.github.syr0ws.crafter.database.config;

import com.github.syr0ws.crafter.database.driver.DatabaseDriver;

/**
 * Represents the configuration for a database connection.
 */
public class DatabaseConfig {

    private DatabaseDriver driver;
    private String database;
    private String host;
    private int port;
    private String user;
    private String password;

    /**
     * Returns the database name.
     *
     * @return database name
     */
    public String getDatabase() {
        return this.database;
    }

    /**
     * Sets the database name.
     *
     * @param database database name
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * Returns the host.
     *
     * @return host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Sets the host.
     *
     * @param host host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Returns the port.
     *
     * @return port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Sets the port.
     *
     * @param port port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns the user.
     *
     * @return user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the password.
     *
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the {@link DatabaseDriver}.
     *
     * @return database driver
     */
    public DatabaseDriver getDriver() {
        return this.driver;
    }

    /**
     * Sets the {@link DatabaseDriver}.
     *
     * @param driver database driver
     */
    public void setDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
}
