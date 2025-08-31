package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;

/**
 * Provides a {@link Database} instance.
 */
public interface DatabaseProvider {

    /**
     * Provides a {@link Database} using the given context.
     *
     * @param context context for providing the database
     * @return the provided database
     */
    Database provide(DatabaseProviderContext context);

    /**
     * Returns the {@link DatabaseDriver} associated with this provider.
     *
     * @return database driver
     */
    DatabaseDriver getDriver();
}
