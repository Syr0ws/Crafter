package com.github.syr0ws.crafter.database.config;

/**
 * Configuration parameters for establishing a connection to a database.
 *
 * @param host     the hostname or IP address of the database server
 * @param port     the port number on which the database server is listening
 * @param database the name of the database to connect to
 * @param username the username for authenticating with the database
 * @param password the password for authenticating with the database
 */
public record DatabaseConnectionConfig(String host, int port, String database, String username, String password) {

}
