package com.github.syr0ws.crafter.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Observable {

    private final Set<Observer> observers = new HashSet<>();

    public void addObserver(Observer observer) {

        if(observer == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }

        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {

        if(observer == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }

        this.observers.remove(observer);
    }

    public boolean hasObserver(Observer observer) {

        if(observer == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }

        return this.observers.contains(observer);
    }

    public void notifyObservers() {
        this.observers.forEach(Observer::notifyChange);
    }

    public Set<Observer> getObservers() {
        return Collections.unmodifiableSet(this.observers);
    }
}
