package com.github.syr0ws.crafter.database.migration;

import com.github.syr0ws.crafter.database.Database;
import com.github.syr0ws.crafter.database.DatabaseDriver;
import com.github.syr0ws.crafter.database.util.ScriptRunner;
import com.github.syr0ws.crafter.util.Validate;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

/**
 * A simple {@link DatabaseMigrator} implementation that is able to run a single SQL script.
 */
public class SimpleMigrator implements DatabaseMigrator {

    private final Plugin plugin;
    private final String initScript;

    /**
     * Constructs a new {@code SimpleMigrator} for the given plugin. The plugin must contain
     * the database migration schemes in its resources folder.
     *
     * @param plugin the plugin used
     * @param initScript the path of the script to migrate in the plugin's resources folder
     */
    public SimpleMigrator(Plugin plugin, String initScript) {
        Validate.notNull(plugin, "plugin cannot be null");
        Validate.notEmpty(initScript, "initScript cannot be null or empty");

        this.initScript = initScript;
        this.plugin = plugin;
    }

    @Override
    public void migrate(Database database) throws Exception {
        Validate.notNull(database, "database cannot be null");

        DatabaseDriver driver = database.getDriver();
        String initScriptResource = String.format("database/%s/%s", driver.getDriverName(), this.initScript);

        try (Connection connection = database.getConnection();
             InputStream stream = this.plugin.getResource(initScriptResource)) {

            if (stream == null) {
                throw new IOException(String.format("Resource '%s' not found", initScriptResource));
            }

            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setStopOnError(true);
            scriptRunner.runScript(new InputStreamReader(stream));
        }
    }
}
