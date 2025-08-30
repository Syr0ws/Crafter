package com.github.syr0ws.crafter.database;

import com.github.syr0ws.crafter.database.exception.UnsupportedDatabaseDriverException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.migration.DatabaseMigrator;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPoolFactory;
import com.github.syr0ws.crafter.database.provider.DatabaseProvider;
import com.github.syr0ws.crafter.database.provider.DatabaseProviderContext;
import com.github.syr0ws.crafter.util.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DatabaseService {

    private final Map<DatabaseDriver, DatabaseProvider> providers = new HashMap<>();

    public Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory factory) throws Exception {
        return this.setupDatabase(config, factory, null);
    }

    public Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory poolFactory, DatabaseMigrator migrator) throws Exception {

        DatabaseDriver driver = config.getDriver();

        if(driver == null) {
            throw new IllegalArgumentException("driver cannot be null in DatabaseConfig");
        }

        // Retrieving the database provider corresponding to the driver.
        DatabaseProvider provider = this.getProvider(driver)
                .orElseThrow(() -> new UnsupportedDatabaseDriverException("No DatabaseProvider found for driver '%s'".formatted(driver.getDriverName())));

        // Creating and opening the connection pool.
        DatabaseConnectionPool pool = poolFactory.createConnectionPool(config);
        pool.open(config);

        // Creating the database.
        DatabaseProviderContext context = new DatabaseProviderContext(pool, config);

        Database database = provider.provide(context);
        database.createDatabaseIfNotExists();

        // Doing the migration if a migrator has been provided.
        if(migrator != null) {
            migrator.migrate(database);
        }

        return database;
    }

    public void addProvider(DatabaseProvider provider) {
        Validate.notNull(provider, "provider cannot be null");
        DatabaseDriver driver = provider.getDriver();
        this.providers.put(driver, provider);
    }

    private Optional<DatabaseProvider> getProvider(DatabaseDriver driver) {
        return Optional.ofNullable(this.providers.get(driver));
    }
}
