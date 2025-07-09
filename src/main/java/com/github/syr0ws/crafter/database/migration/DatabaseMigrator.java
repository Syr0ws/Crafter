package com.github.syr0ws.crafter.database.migration;

import com.github.syr0ws.crafter.database.Database;

/**
 * Database schema migration handler.
 */
public interface DatabaseMigrator {

    /**
     * Performs database schema migration using the given {@link Database} instance.
     *
     * @param database the database to migrate
     */
    void migrate(Database database);
}
