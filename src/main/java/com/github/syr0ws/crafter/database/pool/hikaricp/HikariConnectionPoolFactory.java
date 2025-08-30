package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.exception.UnsupportedDatabaseDriverException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPoolFactory;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class HikariConnectionPoolFactory implements DatabaseConnectionPoolFactory {

    private final Plugin plugin;

    public HikariConnectionPoolFactory(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
    }

    @Override
    public DatabaseConnectionPool createConnectionPool(DatabaseConfig config) throws UnsupportedDatabaseDriverException {

        DatabaseDriver driver = config.getDriver();

        HikariConfigAdapter adapter = driver == DatabaseDriver.SQLITE ?
                new HikariSQLiteConfigAdapter(this.plugin) :
                new HikariStandardConfigAdapter();

        return new HikariConnectionPool(adapter);
    }
}
