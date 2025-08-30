package com.github.syr0ws.crafter.database.driver;

public enum CommonDatabaseDriver implements DatabaseDriver {

    SQLITE("sqlite", "org.sqlite.JDBC"),
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver"),
    MARIADB("mariadb", "org.mariadb.jdbc.Driver"),
    POSTGRESQL("postgresql", "org.postgresql.Driver");

    private final String driverName;
    private final String driverClass;

    CommonDatabaseDriver(String driverName, String driverClass) {
        this.driverName = driverName;
        this.driverClass = driverClass;
    }

    @Override
    public String getDriverName() {
        return this.driverName;
    }

    @Override
    public String getDriverClass() {
        return this.driverClass;
    }
}
