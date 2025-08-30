package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.exception.InvalidDatabaseConfigException;
import com.zaxxer.hikari.HikariConfig;

public interface HikariConfigAdapter {

    HikariConfig getHikariConfig(DatabaseConfig config) throws InvalidDatabaseConfigException;
}
