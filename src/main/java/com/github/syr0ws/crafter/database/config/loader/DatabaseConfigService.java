package com.github.syr0ws.crafter.database.config.loader;

import com.github.syr0ws.crafter.config.ConfigurationException;
import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.driver.SupportedDriverList;

public interface DatabaseConfigService<T> {

    DatabaseConfig loadConfig(SupportedDriverList driverList, T dataSource) throws ConfigurationException;

    DatabaseConfig loadConfig(DatabaseDriver driver, T dataSource) throws ConfigurationException;
}
