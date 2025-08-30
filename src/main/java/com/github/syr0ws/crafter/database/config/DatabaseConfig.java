package com.github.syr0ws.crafter.database.config;

import com.github.syr0ws.crafter.database.driver.DatabaseDriver;

public class DatabaseConfig {

    private DatabaseDriver driver;
    private String database;
    private String host;
    private int port;
    private String user;
    private String password;

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DatabaseDriver getDriver() {
        return this.driver;
    }

    public void setDriver(DatabaseDriver driver) {
        this.driver = driver;
    }
}
