package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.exception.InvalidDatabaseConfigException;
import com.zaxxer.hikari.HikariConfig;

/**
 * Converts a {@link DatabaseConfig} into a {@link HikariConfig} for HikariCP.
 */
public interface HikariConfigAdapter {

    /**
     * Returns a {@link HikariConfig} based on the given {@link DatabaseConfig}.
     *
     * @param config database configuration
     * @return HikariCP configuration
     * @throws InvalidDatabaseConfigException if the configuration is invalid
     */
    HikariConfig getHikariConfig(DatabaseConfig config) throws InvalidDatabaseConfigException;
}
