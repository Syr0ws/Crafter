package com.github.syr0ws.crafter.database.init;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.DatabaseFactory;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfigLoader;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPoolFactory;
import com.github.syr0ws.crafter.database.exception.UnsupportedDatabaseDriverException;
import com.github.syr0ws.crafter.database.migration.DatabaseMigrator;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.plugin.Plugin;

/**
 * Responsible for initializing and creating a {@link Database} instance.
 */
public class DatabaseInitializer {

    private final Plugin plugin;

    private DatabaseConnectionPoolFactory poolFactory;
    private DatabaseMigrator migrator;
    private DatabaseDriver[] drivers;

    /**
     * Constructs a new {@code DatabaseInitializer} for the given plugin context.
     *
     * @param plugin the plugin in which the database will be used
     * @throws IllegalArgumentException if {@code plugin} is {@code null}
     */
    public DatabaseInitializer(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
    }

    /**
     * Initializes and returns a configured {@link Database} instance.
     * <p>
     * This process includes:
     * <ul>
     *   <li>Loading the database connection configuration</li>
     *   <li>Checking if the selected {@link DatabaseDriver} is supported</li>
     *   <li>Creating and opening the {@link DatabaseConnectionPool}</li>
     *   <li>Creating the {@link Database}</li>
     *   <li>Running migrations via {@link DatabaseMigrator}, if set</li>
     * </ul>
     *
     * @return a fully initialized {@link Database} instance
     * @throws Exception if configuration is invalid or initialization fails
     */
    public Database loadDatabase() throws Exception {
        Validate.notNull(this.poolFactory, "No pool factory specified");

        // Loading database configuration.
        DatabaseConnectionConfigLoader loader = new DatabaseConnectionConfigLoader(this.plugin);
        DatabaseConnectionConfig config = loader.loadConfig();
        DatabaseDriver driver = loader.loadDriver();

        // Creating database factory and checking that the driver is supported.
        DatabaseFactory factory = new DatabaseFactory();
        factory.support(this.drivers);

        if (!factory.isSupported(driver)) {
            throw new UnsupportedDatabaseDriverException("Database driver '%s' is not supported".formatted(driver.getDriverName()));
        }

        // Creating connection pool.
        DatabaseConnectionPool pool = this.poolFactory.createPool(driver, config);
        pool.open();

        // Creating database.
        Database database = factory.createDatabase(driver, config, pool);
        database.createDatabaseIfNotExists();

        // Schema migration.
        if (this.migrator != null) {
            this.migrator.migrate(database);
        }

        return database;
    }

    /**
     * Sets the list of supported {@link DatabaseDriver}s for this initializer.
     *
     * @param drivers the drivers to support
     * @throws IllegalArgumentException if {@code drivers} is {@code null}
     */
    public void setSupportedDrivers(DatabaseDriver... drivers) {
        Validate.notNull(drivers, "drivers cannot be null");
        this.drivers = drivers;
    }

    /**
     * Sets the {@link DatabaseConnectionPoolFactory} used to create connection pools.
     *
     * @param factory the pool factory
     * @throws IllegalArgumentException if {@code factory} is {@code null}
     */
    public void setConnectionPoolFactory(DatabaseConnectionPoolFactory factory) {
        Validate.notNull(factory, "factory cannot be null");
        this.poolFactory = factory;
    }

    /**
     * Sets the {@link DatabaseMigrator} used to handle schema migration.
     *
     * @param migrator the database migrator
     * @throws IllegalArgumentException if {@code migrator} is {@code null}
     */
    public void setMigrator(DatabaseMigrator migrator) {
        Validate.notNull(migrator, "migrator cannot be null");
        this.migrator = migrator;
    }
}
