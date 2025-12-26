package com.github.syr0ws.crafter.version;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for manipulating Minecraft server versions.
 */
public class ServerVersionUtil {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d\\.\\d+\\.\\d+)");

    /**
     * Returns the current Minecraft version.
     *
     * @return the Minecraft version string (ex: 1.21.6)
     * @throws ServerVersionNotFoundException if the version cannot be parsed
     */
    public static String getCurrentMinecraftVersion() throws ServerVersionNotFoundException {

        // Bukkit version should always contain the Minecraft version.
        String bukkitVersion = Bukkit.getVersion();
        Matcher matcher = VERSION_PATTERN.matcher(bukkitVersion);

        if(!matcher.find()) {
            throw new ServerVersionNotFoundException("Cannot parse server version from '%s'".formatted(bukkitVersion));
        }

        return matcher.group(1);
    }

    /**
     * Returns the current NMS version corresponding to the running Minecraft server.
     *
     * @return the current NMS version
     * @throws ServerVersionNotFoundException if the Minecraft version is not supported or cannot be determined
     */
    public static NMSVersion getCurrentNMSVersion() throws ServerVersionNotFoundException {

        String minecraftVersion = getCurrentMinecraftVersion();

        ServerVersion serverVersion = ServerVersion.getServerVersion(minecraftVersion)
                .orElseThrow(() -> new ServerVersionNotFoundException("Unsupported Minecraft version '%s'".formatted(minecraftVersion)));

        return serverVersion.getNmsVersion();
    }
}
