package com.github.syr0ws.crafter.database;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.migration.DatabaseMigrator;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.pool.DatabaseConnectionPoolFactory;
import com.github.syr0ws.crafter.database.provider.*;

public class SimpleDatabaseService implements DatabaseService {

    @Override
    public Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory factory) throws Exception {
        return this.setupDatabase(config, factory, null);
    }

    @Override
    public Database setupDatabase(DatabaseConfig config, DatabaseConnectionPoolFactory poolFactory, DatabaseMigrator migrator) throws Exception {

        DatabaseDriver driver = config.getDriver();

        if(driver == null) {
            throw new IllegalArgumentException("driver cannot be null in DatabaseConfig");
        }

        // Retrieving the database provider corresponding to the driver.
        DatabaseProvider provider = this.getProvider(driver);

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

    private DatabaseProvider getProvider(DatabaseDriver driver) {
        return switch (driver) {
            case SQLITE -> new SQLiteDatabaseProvider();
            case MYSQL -> new MySQLDatabaseProvider();
            case MARIADB -> new MariaDBDatabaseProvider();
            case POSTGRESQL -> new PostgreSQLDatabaseProvider();
        };
    }
}
