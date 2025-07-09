package com.github.syr0ws.crafter.database.connection.hikaricp;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPoolFactory;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.plugin.Plugin;

public class HikariConnectionPoolFactory implements DatabaseConnectionPoolFactory {

    private final Plugin plugin;

    public HikariConnectionPoolFactory(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
    }

    @Override
    public DatabaseConnectionPool createPool(DatabaseDriver driver, DatabaseConnectionConfig config) {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(config, "config cannot be null");

        if(driver == DatabaseDriver.SQLITE) {
            return new SQLiteDatabaseConnectionPool(driver, config, this.plugin);
        }

        return new RemoteDatabaseConnectionPool(driver, config);
    }
}
