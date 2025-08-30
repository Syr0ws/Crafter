package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.CommonDatabaseDriver;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostgreSQLDatabase extends RemoteDatabase {

    public PostgreSQLDatabase(DatabaseConfig config, DatabaseConnectionPool pool) {
        super(config, pool);
    }

    @Override
    @SuppressWarnings("all")
    public void createDatabaseIfNotExists() throws Exception {

        DatabaseConfig config = super.getConfig();
        Validate.notNull(config.getDatabase(), "database name cannot be null");

        String databaseExistQuery = "SELECT 1 FROM pg_database WHERE datname = ?;";

        try (Connection connection = super.createServerConnection();
             PreparedStatement statement = connection.prepareStatement(databaseExistQuery)) {

            statement.setString(1, config.getDatabase());
            ResultSet result = statement.executeQuery();

            // If there is a result, the database already exists.
            if (result.next()) {
                return;
            }

            String databaseCreateQuery = String.format("CREATE DATABASE %s;", config.getDatabase());

            try (PreparedStatement createStatement = connection.prepareStatement(databaseCreateQuery)) {
                createStatement.execute();
            }
        }
    }

    @Override
    public DatabaseDriver getDriver() {
        return CommonDatabaseDriver.POSTGRESQL;
    }
}
