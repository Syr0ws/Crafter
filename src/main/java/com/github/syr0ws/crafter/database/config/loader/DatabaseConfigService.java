package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;

import java.util.Optional;

public interface DatabaseConfigService<T> {

    DatabaseConfig loadConnectionConfig(SupportedDriverList driverList, T dataSource) throws ConfigurationException;

    DatabaseConfig loadConnectionConfig(DatabaseDriver driver, T dataSource) throws ConfigurationException;

    void addConfigLoader(DatabaseDriver driver, DatabaseConfigLoader<T> loader);

    void removeConfigLoader(DatabaseDriver driver);

    boolean hasConfigLoader(DatabaseDriver driver);

    Optional<DatabaseConfigLoader<T>> getConfigLoader(DatabaseDriver driver);
}
