package com.github.syr0ws.crafter.database.connection.hikaricp;

import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.config.DatabaseConnectionConfig;
import com.github.syr0ws.crafter.util.Validate;
import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.Plugin;

public class SQLiteDatabaseConnectionPool extends HikariConnectionPool {

    private static final String DATABASE_FILE = "database.db";

    private final Plugin plugin;

    public SQLiteDatabaseConnectionPool(DatabaseDriver driver, DatabaseConnectionConfig config, Plugin plugin) {
        super(driver, config);
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
    }

    @Override
    protected String getUrl(DatabaseDriver driver, DatabaseConnectionConfig config) {
        String path = this.plugin.getDataFolder().getAbsolutePath() + "/" + DATABASE_FILE;
        return "jdbc:sqlite:" + path;
    }

    @Override
    protected HikariConfig getHikariConfig(DatabaseDriver driver, DatabaseConnectionConfig config) {
        HikariConfig hikariConfig = super.getHikariConfig(driver, config);
        hikariConfig.setConnectionInitSql("PRAGMA foreign_keys = ON;");
        return hikariConfig;
    }
}
