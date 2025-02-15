package com.github.syr0ws.crafter.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

/**
 * Represents a promise-like mechanism for handling asynchronous operations.
 *
 * @param <T> the type of the result produced by the promise.
 */
public class Promise<T> {

    private final PromiseExecutor<T> executor;

    private Consumer<T> then;
    private Consumer<Throwable> except;
    private Runnable complete;

    /**
     * Constructs a new {@code Promise} with the specified executor.
     *
     * @param executor the executor responsible for resolving the promise.
     * @throws IllegalArgumentException if the executor is {@code null}.
     */
    public Promise(PromiseExecutor<T> executor) {
        Validate.notNull(executor, "executor cannot be null");

        this.executor = executor;
    }

    /**
     * Registers a callback to be executed when the promise resolves successfully.
     *
     * @param consumer the consumer to handle the resolved value.
     * @return this promise instance.
     * @throws IllegalArgumentException if the consumer is {@code null}.
     */
    public Promise<T> then(Consumer<T> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");

        this.then = consumer;
        return this;
    }

    /**
     * Registers a callback to be executed when an exception occurs.
     *
     * @param consumer the consumer to handle the exception.
     * @return this promise instance.
     * @throws IllegalArgumentException if the consumer is {@code null}.
     */
    public Promise<T> except(Consumer<Throwable> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");

        this.except = consumer;
        return this;
    }

    /**
     * Registers a callback to be executed when the promise is completed.
     *
     * @param runnable the runnable to execute upon completion.
     * @return this promise instance.
     * @throws IllegalArgumentException if the runnable is {@code null}.
     */
    public Promise<T> complete(Runnable runnable) {
        Validate.notNull(runnable, "runnable cannot be null");

        this.complete = runnable;
        return this;
    }

    /**
     * Resolves the promise synchronously on the main server thread.
     *
     * @param plugin the plugin instance required to schedule the task.
     * @throws IllegalArgumentException if the plugin is {@code null}.
     */
    public void resolveSync(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        Bukkit.getScheduler().runTask(plugin, this::resolve);
    }

    /**
     * Resolves the promise asynchronously on a separate thread.
     *
     * @param plugin the plugin instance required to schedule the task.
     * @throws IllegalArgumentException if the plugin is {@code null}.
     */
    public void resolveAsync(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");

        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::resolve);
    }

    private void onThen(T value) {
        if(this.then != null) {
            this.then.accept(value);
        }
    }

    private void onExcept(Throwable throwable) {
        if(this.except != null) {
            this.except.accept(throwable);
        }
    }

    private void onComplete() {
        if(this.complete != null) {
            this.complete.run();
        }
    }

    private void resolve() {
        try {
            this.executor.execute(this::onThen, this::onExcept);
        } catch (Exception exception) {
            this.onExcept(exception);
        }
        this.onComplete();
    }
}
