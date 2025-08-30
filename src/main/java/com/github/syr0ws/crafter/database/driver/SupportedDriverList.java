package com.github.syr0ws.crafter.database.driver;

import com.github.syr0ws.crafter.util.Validate;

import java.util.*;

public class SupportedDriverList {

    private final Map<String, DatabaseDriver> drivers = new HashMap<>();

    public void addDriver(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        this.drivers.put(driver.getDriverName(), driver);
    }

    public void addDrivers(DatabaseDriver... drivers) {
        Validate.notNull(drivers, "drivers cannot be null");
        Arrays.stream(drivers).forEach(this::addDriver);
    }

    public void removeDriver(DatabaseDriver driver) {
        Validate.notNull(driver, "driver cannot be null");
        this.drivers.remove(driver.getDriverName());
    }

    public List<DatabaseDriver> getDrivers() {
        return new ArrayList<>(this.drivers.values());
    }
}
