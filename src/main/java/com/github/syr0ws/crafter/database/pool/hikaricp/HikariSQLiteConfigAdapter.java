package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.util.Validate;
import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.Plugin;

/**
 * {@link HikariConfigAdapter} implementation for SQLite databases.
 */
public class HikariSQLiteConfigAdapter implements HikariConfigAdapter {

    private static final String DATABASE_FILE = "database.db";

    private final Plugin plugin;

    public HikariSQLiteConfigAdapter(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
    }

    @Override
    public HikariConfig getHikariConfig(DatabaseConfig config) {

        String url = this.getJdbcUrl();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(DatabaseDriver.SQLITE.getDriverClass());
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setAutoCommit(true);
        hikariConfig.setConnectionInitSql("PRAGMA foreign_keys = ON;");

        return hikariConfig;
    }

    private String getJdbcUrl() {
        String path = this.plugin.getDataFolder().getAbsolutePath() + "/" + DATABASE_FILE;
        return "jdbc:%s:%s".formatted(DatabaseDriver.SQLITE.getDriverName(), path);
    }
}
