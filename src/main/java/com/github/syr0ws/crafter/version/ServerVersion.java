package com.github.syr0ws.crafter.version;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents supported Minecraft server versions and their corresponding NMS versions.
 */
public enum ServerVersion {

    v1_21_6(NMSVersion.v1_21_R5),
    v1_21_7(NMSVersion.v1_21_R5),
    v1_21_8(NMSVersion.v1_21_R5),
    v1_21_9(NMSVersion.v1_21_R6),
    v1_21_10(NMSVersion.v1_21_R6),
    v1_21_11(NMSVersion.v1_21_R7);

    private final String minecraftVersion;
    private final NMSVersion nmsVersion;

    /**
     * Constructs a {@code ServerVersion} with its corresponding NMS version.
     *
     * @param nmsVersion the NMS version
     */
    ServerVersion(NMSVersion nmsVersion) {
        this.minecraftVersion = this.name().substring(1).replace("_", ".");
        this.nmsVersion = nmsVersion;
    }

    /**
     * Returns the Minecraft version represented by this enum constant.
     *
     * @return the Minecraft version
     */
    private String getMinecraftVersion() {
        return this.minecraftVersion;
    }

    /**
     * Returns the NMS version string for this server version.
     *
     * @return the NMS version
     */
    public NMSVersion getNmsVersion() {
        return nmsVersion;
    }

    /**
     * Returns the {@link ServerVersion} matching the given Minecraft version string.
     *
     * @param minecraftVersion the Minecraft version to look up
     * @return an {@link Optional} containing the matching {@code ServerVersion}, or empty if not found
     * @throws IllegalArgumentException if {@code minecraftVersion} is {@code null}
     */
    public static Optional<ServerVersion> getServerVersion(String minecraftVersion) {

        if(minecraftVersion == null) {
            throw new IllegalArgumentException("minecraftVersion cannot be null");
        }

        return Arrays.stream(values())
                .filter(version -> version.getMinecraftVersion().equals(minecraftVersion))
                .findFirst();
    }
}
