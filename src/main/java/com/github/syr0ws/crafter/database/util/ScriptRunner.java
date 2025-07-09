package com.github.syr0ws.crafter.database.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Original implementation by Clinton Begin from mybatis-3.
 *
 * <p>Some modifications have been made in this class by removing some methods and features which
 * are not useful for the current project.</p>
 *
 * @author Clinton Begin (original author)
 * @author Syr0ws (modified by)
 */
public class ScriptRunner {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String DEFAULT_DELIMITER = ";";

    private static final Pattern DELIMITER_PATTERN = Pattern
            .compile("^\\s*((--)|(//))?\\s*(//)?\\s*@DELIMITER\\s+([^\\s]+)", Pattern.CASE_INSENSITIVE);

    private final Connection connection;

    private boolean stopOnError;
    private boolean throwWarning;
    private boolean autoCommit;
    private boolean removeCRs;
    private boolean escapeProcessing = true;

    private String delimiter = DEFAULT_DELIMITER;
    private boolean fullLineDelimiter;

    public ScriptRunner(Connection connection) {
        this.connection = connection;
    }

    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }

    public void setThrowWarning(boolean throwWarning) {
        this.throwWarning = throwWarning;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void setRemoveCRs(boolean removeCRs) {
        this.removeCRs = removeCRs;
    }

    public void setEscapeProcessing(boolean escapeProcessing) {
        this.escapeProcessing = escapeProcessing;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setFullLineDelimiter(boolean fullLineDelimiter) {
        this.fullLineDelimiter = fullLineDelimiter;
    }

    public void runScript(Reader reader) throws SQLException, IOException {
        setAutoCommit();
        try {
            executeLineByLine(reader);
        } finally {
            rollbackConnection();
        }
    }

    private void executeLineByLine(Reader reader) throws SQLException, IOException {

        StringBuilder command = new StringBuilder();
        BufferedReader lineReader = new BufferedReader(reader);

        String line;
        while ((line = lineReader.readLine()) != null) {
            handleLine(command, line);
        }

        commitConnection();
        checkForMissingLineTerminator(command);
    }

    private void setAutoCommit() throws SQLException {
        if (autoCommit != connection.getAutoCommit()) {
            connection.setAutoCommit(autoCommit);
        }
    }

    private void commitConnection() throws SQLException {
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
    }

    private void rollbackConnection() throws SQLException {
        if (!connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    private void checkForMissingLineTerminator(StringBuilder command) throws SQLException {
        if (command != null && !command.toString().trim().isEmpty()) {
            throw new SQLException("Line missing end-of-line terminator (" + delimiter + ") => " + command);
        }
    }

    private void handleLine(StringBuilder command, String line) throws SQLException {
        String trimmedLine = line.trim();
        if (lineIsComment(trimmedLine)) {
            Matcher matcher = DELIMITER_PATTERN.matcher(trimmedLine);
            if (matcher.find()) {
                delimiter = matcher.group(5);
            }
        } else if (commandReadyToExecute(trimmedLine)) {
            command.append(line, 0, line.lastIndexOf(delimiter));
            command.append(LINE_SEPARATOR);
            executeStatement(command.toString());
            command.setLength(0);
        } else if (!trimmedLine.isEmpty()) {
            command.append(line);
            command.append(LINE_SEPARATOR);
        }
    }

    private boolean lineIsComment(String trimmedLine) {
        return trimmedLine.startsWith("//") || trimmedLine.startsWith("--");
    }

    private boolean commandReadyToExecute(String trimmedLine) {
        // issue #561 remove anything after the delimiter
        return !fullLineDelimiter && trimmedLine.contains(delimiter) || fullLineDelimiter && trimmedLine.equals(delimiter);
    }

    private void executeStatement(String command) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.setEscapeProcessing(escapeProcessing);
            String sql = command;
            if (removeCRs) {
                sql = sql.replace("\r\n", "\n");
            }
            try {
                boolean hasResults = statement.execute(sql);
                // DO NOT try to 'improve' the condition even if IDE tells you to!
                // It's important that getUpdateCount() is called here.
                while (!(!hasResults && statement.getUpdateCount() == -1)) {
                    checkWarnings(statement);
                    hasResults = statement.getMoreResults();
                }
            } catch (SQLWarning e) {
                throw e;
            } catch (SQLException e) {
                if (stopOnError) {
                    throw e;
                }
            }
        }
    }

    private void checkWarnings(Statement statement) throws SQLException {
        if (!throwWarning) {
            return;
        }
        // In Oracle, CREATE PROCEDURE, FUNCTION, etc. returns warning
        // instead of throwing exception if there is compilation error.
        SQLWarning warning = statement.getWarnings();
        if (warning != null) {
            throw warning;
        }
    }
}
