package com.github.syr0ws.crafter.database.driver;

import com.github.syr0ws.crafter.util.Validate;

import java.util.*;

/**
 * Holds a collection of supported {@link DatabaseDriver} instances.
 */
public class SupportedDriverList {

    private final Set<DatabaseDriver> drivers = new HashSet<>();

    /**
     * Adds a driver to the list.
     *
     * @param driver database driver
     */
    public void addDriver(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        this.drivers.add(driver);
    }

    /**
     * Adds multiple drivers to the list.
     *
     * @param drivers array of database drivers
     */
    public void addDrivers(DatabaseDriver... drivers) {
        Validate.notNull(drivers, "drivers cannot be null");
        Arrays.stream(drivers).forEach(this::addDriver);
    }

    /**
     * Removes a driver from the list.
     *
     * @param driver database driver
     */
    public void removeDriver(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        this.drivers.remove(driver);
    }

    /**
     * Returns the list of supported drivers.
     *
     * @return list of database drivers
     */
    public Collection<DatabaseDriver> getDrivers() {
        return Collections.unmodifiableCollection(this.drivers);
    }
}
