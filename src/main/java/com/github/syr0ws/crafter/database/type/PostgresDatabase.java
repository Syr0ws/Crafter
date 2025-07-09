package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostgresDatabase extends RemoteDatabase {

    public PostgresDatabase(DatabaseConnectionPool pool, DatabaseConnectionConfig config) {
        super(pool, config);
    }

    @Override
    @SuppressWarnings("all")
    public void createDatabaseIfNotExists() throws Exception {

        DatabaseConnectionConfig config = super.getConfig();
        String databaseExistQuery = "SELECT 1 FROM pg_database WHERE datname = ?;";

        try (Connection connection = super.getServerConnection();
             PreparedStatement statement = connection.prepareStatement(databaseExistQuery)) {

            statement.setString(1, config.database());
            ResultSet result = statement.executeQuery();

            // If there is a result, the database already exists.
            if (result.next()) {
                return;
            }

            String databaseCreateQuery = String.format("CREATE DATABASE %s;", config.database());

            try (PreparedStatement createStatement = connection.prepareStatement(databaseCreateQuery)) {
                createStatement.execute();
            }
        }
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.POSTGRESQL;
    }
}
