package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;

public interface DatabaseConfigLoader<T> {

    DatabaseConfig loadDatabaseConfig(T dataSource) throws ConfigurationException;
}
