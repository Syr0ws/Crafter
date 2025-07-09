package com.github.syr0ws.crafter.database;

/**
 * Enumeration of supported database drivers.
 */
public enum DatabaseDriver {

    SQLITE("sqlite", "org.sqlite.JDBC"),
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver"),
    MARIADB("mariadb", "org.mariadb.jdbc.Driver"),
    POSTGRESQL("postgresql", "org.postgresql.Driver");

    private final String driverName;
    private final String driverClass;

    DatabaseDriver(String driverName, String driverClass) {
        this.driverName = driverName;
        this.driverClass = driverClass;
    }

    /**
     * Returns the name of the database driver.
     *
     * @return the driver name
     */
    public String getDriverName() {
        return this.driverName;
    }

    /**
     * Returns the fully qualified class name of the database driver.
     *
     * @return the driver class name
     */
    public String getDriverClass() {
        return this.driverClass;
    }
}
