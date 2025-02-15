package com.github.syr0ws.crafter.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an observable entity that maintains a set of observers
 * and notifies them of changes.
 */
public class Observable {

    private final Set<Observer> observers = new HashSet<>();

    /**
     * Adds an observer to the observable.
     *
     * @param observer the observer to add.
     * @throws IllegalArgumentException if the observer is {@code null}.
     */
    public void addObserver(Observer observer) {
        Validate.notNull(observer, "observer cannot be null");

        this.observers.add(observer);
    }

    /**
     * Removes an observer from the observable.
     *
     * @param observer the observer to remove.
     * @throws IllegalArgumentException if the observer is {@code null}.
     */
    public void removeObserver(Observer observer) {
        Validate.notNull(observer, "observer cannot be null");

        this.observers.remove(observer);
    }

    /**
     * Checks if a specific observer is registered.
     *
     * @param observer the observer to check.
     * @return {@code true} if the observer is registered, {@code false} otherwise.
     * @throws IllegalArgumentException if the observer is {@code null}.
     */
    public boolean hasObserver(Observer observer) {
        Validate.notNull(observer, "observer cannot be null");

        return this.observers.contains(observer);
    }

    /**
     * Notifies all registered observers of a change.
     */
    public void notifyObservers() {
        this.observers.forEach(Observer::notifyChange);
    }

    /**
     * Gets the observers registered in the observable.
     *
     * @return an unmodifiable set of observers.
     */
    public Set<Observer> getObservers() {
        return Collections.unmodifiableSet(this.observers);
    }
}
