package com.github.syr0ws.crafter.database;

import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.database.connection.DatabaseConnectionPool;
import com.github.syr0ws.crafter.database.type.MySQLDatabase;
import com.github.syr0ws.crafter.database.type.PostgresDatabase;
import com.github.syr0ws.crafter.database.type.SQLiteDatabase;
import com.github.syr0ws.crafter.util.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Factory class for creating {@link Database} instances based on a specified
 * {@link DatabaseDriver}.
 */
public class DatabaseFactory {

    private final List<DatabaseDriver> supported = new ArrayList<>();

    /**
     * Adds the specified database drivers to the list of supported drivers.
     *
     * @param drivers the drivers to support
     * @throws IllegalArgumentException if {@code drivers} is {@code null}
     */
    public void support(DatabaseDriver... drivers) {
        Validate.notNull(drivers, "drivers cannot be null");
        this.supported.addAll(Arrays.asList(drivers));
    }

    /**
     * Checks whether the given {@link DatabaseDriver} is supported by this factory.
     *
     * @param driver the driver to check
     * @return {@code true} if the driver is supported; {@code false} otherwise
     * @throws IllegalArgumentException if {@code driver} is {@code null}
     */
    public boolean isSupported(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        return this.supported.contains(driver);
    }

    /**
     * Creates a new {@link Database} instance given a {@link DatabaseDriver}.
     * <p>
     * Supported drivers include:
     * <ul>
     *   <li>{@link DatabaseDriver#MYSQL}</li>
     *   <li>{@link DatabaseDriver#MARIADB}</li>
     *   <li>{@link DatabaseDriver#POSTGRESQL}</li>
     *   <li>{@link DatabaseDriver#SQLITE}</li>
     * </ul>
     *
     * @param driver the database driver
     * @param config the connection configuration
     * @param pool   the connection pool
     * @return a new {@link Database} implementation corresponding to the driver
     * @throws IllegalArgumentException      if any argument is {@code null}
     * @throws UnsupportedOperationException if the driver is not supported
     */
    public Database createDatabase(DatabaseDriver driver, DatabaseConnectionConfig config, DatabaseConnectionPool pool) {
        Validate.notNull(driver, "driver cannot be null");
        Validate.notNull(config, "config cannot be null");
        Validate.notNull(pool, "pool cannot be null");

        if (!this.supported.contains(driver)) {
            throw new UnsupportedOperationException("Unsupported database driver: " + driver.getDriverName());
        }

        return switch (driver) {
            case MYSQL, MARIADB -> new MySQLDatabase(pool, config);
            case POSTGRESQL -> new PostgresDatabase(pool, config);
            case SQLITE -> new SQLiteDatabase(pool, config);
        };
    }
}
