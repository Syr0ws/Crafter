package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MySQLDatabase extends RemoteDatabase {

    public MySQLDatabase(DatabaseConnectionPool pool, DatabaseConnectionConfig config) {
        super(pool, config);
    }

    @Override
    @SuppressWarnings("all")
    public void createDatabaseIfNotExists() throws Exception {

        DatabaseConnectionConfig config = super.getConfig();
        String query = String.format("CREATE DATABASE IF NOT EXISTS %s;", config.database());

        try (Connection connection = super.getServerConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.execute();
        }
    }

    @Override
    public DatabaseDriver getDriver() {
        return DatabaseDriver.MYSQL;
    }
}
