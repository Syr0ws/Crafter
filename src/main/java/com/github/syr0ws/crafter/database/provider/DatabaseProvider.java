package com.github.syr0ws.crafter.database.provider;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;

public interface DatabaseProvider {

    Database provide(DatabaseProviderContext context);

    DatabaseDriver getDriver();
}
