package com.github.syr0ws.crafter.database.pool.hikaricp;

import com.github.syr0ws.crafter.database.config.DatabaseConfig;
import com.github.syr0ws.crafter.database.driver.DatabaseDriver;
import com.github.syr0ws.crafter.database.exception.InvalidDatabaseConfigException;
import com.zaxxer.hikari.HikariConfig;

/**
 * {@link HikariConfigAdapter} implementation for remote databases such as
 * MySQL, MariaDB and PostgreSQL.
 */
public class HikariStandardConfigAdapter implements HikariConfigAdapter {

    @Override
    public HikariConfig getHikariConfig(DatabaseConfig config) throws InvalidDatabaseConfigException {
        String url = this.getJdbcUrl(config);

        if (config.getHost() == null) {
            throw new InvalidDatabaseConfigException("host cannot be null in DatabaseConfig");
        }

        if (config.getPort() <= 0) {
            throw new InvalidDatabaseConfigException("port is invalid in DatabaseConfig");
        }

        if (config.getDatabase() == null) {
            throw new InvalidDatabaseConfigException("database cannot be null in DatabaseConfig");
        }

        if (config.getUser() == null) {
            throw new InvalidDatabaseConfigException("user cannot be null in DatabaseConfig");
        }

        if (config.getPassword() == null) {
            throw new InvalidDatabaseConfigException("password cannot be null in DatabaseConfig");
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(config.getDriver().getDriverClass());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setAutoCommit(true);

        return hikariConfig;
    }

    private String getJdbcUrl(DatabaseConfig config) {
        DatabaseDriver driver = config.getDriver();
        return "jdbc:%s://%s:%d/%s?tcpKeepAlive=true".formatted(
                driver.getDriverName(),
                config.getHost(),
                config.getPort(),
                config.getDatabase()
        );
    }
}
