package com.github.syr0ws.crafter.database.migration;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.plugin.Plugin;
import org.flywaydb.core.Flyway;

/**
 * A {@link com.github.syr0ws.crafter.database.migration.DatabaseMigrator} implementation that uses Flyway for database migration.
 */
public class FlywayMigrator implements DatabaseMigrator {

    private final Plugin plugin;

    /**
     * Constructs a new {@code FlywayMigrator} for the given plugin. The plugin must contain
     * the database migration schemes in its resources folder.
     *
     * @param plugin the plugin used
     */
    public FlywayMigrator(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void migrate(Database database) {
        Validate.notNull(database, "database cannot be null");

        ClassLoader loader = this.plugin.getClass().getClassLoader();
        String location = "classpath:database/" + database.getDriver().getDriverName();

        Flyway flyway = Flyway.configure(loader)
                .dataSource(database.getConnectionPool().getDataSource())
                .baselineOnMigrate(true)
                .locations(location)
                .mixed(true)
                .load();

        flyway.migrate();
    }
}
