package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MariaDBDatabase extends RemoteDatabase {

    public MariaDBDatabase(DatabaseConfig config, DatabaseConnectionPool pool) {
        super(config, pool);
    }

    @Override
    @SuppressWarnings("all")
    public void createDatabaseIfNotExists() throws Exception {

        DatabaseConfig config = super.getConfig();
        Validate.notNull(config.getDatabase(), "database name cannot be null");

        String query = String.format("CREATE DATABASE IF NOT EXISTS %s;", config.getDatabase());

        try (Connection connection = super.createServerConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.execute();
        }
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.MARIADB;
    }
}
