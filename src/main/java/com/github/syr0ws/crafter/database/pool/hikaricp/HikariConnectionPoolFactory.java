package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.exception.UnsupportedDatabaseDriverException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.CommonDatabaseDriver;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPoolFactory;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class HikariConnectionPoolFactory implements DatabaseConnectionPoolFactory {

    private final Map<String, HikariConfigAdapter> adapters = new HashMap<>();

    @Override
    public DatabaseConnectionPool createConnectionPool(DatabaseConfig config) throws UnsupportedDatabaseDriverException {

        DatabaseDriver driver = config.getDriver();
        String driverName = driver.getDriverName();

        if(!this.adapters.containsKey(driverName)) {
            throw new UnsupportedDatabaseDriverException("No adapter found for driver '%s'".formatted(driverName));
        }

        HikariConfigAdapter adapter = this.adapters.get(driverName);

        return new HikariConnectionPool(adapter);
    }

    public void addAdapter(DatabaseDriver driver, HikariConfigAdapter adapter) {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(adapter, "adapter cannot be null");
        this.adapters.put(driver.getDriverName(), adapter);
    }

    public static HikariConnectionPoolFactory create(Plugin plugin) {
        HikariConnectionPoolFactory factory = new HikariConnectionPoolFactory();
        factory.addAdapter(CommonDatabaseDriver.SQLITE, new HikariSQLiteConfigAdapter(plugin));
        factory.addAdapter(CommonDatabaseDriver.MYSQL, new HikariStandardConfigAdapter());
        factory.addAdapter(CommonDatabaseDriver.MARIADB, new HikariStandardConfigAdapter());
        factory.addAdapter(CommonDatabaseDriver.POSTGRESQL, new HikariStandardConfigAdapter());
        return factory;
    }
}
