package com.github.syr0ws.crafter.database.connection.hikaricp;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;

public class RemoteDatabaseConnectionPool extends HikariConnectionPool {

    public RemoteDatabaseConnectionPool(DatabaseDriver driver, DatabaseConnectionConfig config) {
        super(driver, config);
    }

    @Override
    protected String getUrl(DatabaseDriver driver, DatabaseConnectionConfig config) {
        return String.format("jdbc:%s://%s:%d/%s?tcpKeepAlive=true",
                driver.getDriverName(), config.host(), config.port(), config.database()
        );
    }
}
