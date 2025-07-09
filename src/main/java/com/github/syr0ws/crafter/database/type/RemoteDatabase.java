package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class RemoteDatabase extends AbstractDatabase {

    public RemoteDatabase(DatabaseConnectionPool pool, DatabaseConnectionConfig config) {
        super(pool, config);
    }

    protected Connection getServerConnection() throws Exception {

        DatabaseConnectionConfig config = super.getConfig();
        DatabaseDriver driver = this.getDriver();

        Class.forName(driver.getDriverClass());

        String url = String.format("jdbc:%s://%s:%d/", driver.getDriverName(), config.host(), config.port());

        return DriverManager.getConnection(url, config.username(), config.password());
    }
}
