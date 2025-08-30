package com.github.syr0ws.crafter.database.type;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.util.Validate;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class RemoteDatabase extends AbstractDatabase {

    public RemoteDatabase(DatabaseConfig config, DatabaseConnectionPool pool) {
        super(config, pool);
    }

    protected Connection createServerConnection() throws Exception {

        DatabaseConfig config = super.getConfig();
        DatabaseDriver driver = this.getDriver();

        // Checking that the driver is properly loaded.
        Class.forName(driver.getDriverClass());

        // Building database url.
        Validate.notEmpty(config.getHost(), "database host cannot be null or empty");
        Validate.notEmpty(config.getUser(), "database user cannot be null or empty");
        Validate.notNull(config.getPassword(), "database password cannot be null");

        String url = String.format("jdbc:%s://%s:%d/", driver.getDriverName(), config.getHost(), config.getPort());

        return DriverManager.getConnection(url, config.getUser(), config.getPassword());
    }
}
