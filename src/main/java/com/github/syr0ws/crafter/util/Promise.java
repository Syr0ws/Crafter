package com.github.syr0ws.crafter.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

/**
 * Represents a promise-like mechanism for handling asynchronous operations.
 *
 * @param <T> the type of the result produced by the promise.
 */
public class Promise<T> {

    private final PromiseExecutor<T> executor;
    private final Queue<Consumer<T>> thens = new ConcurrentLinkedDeque<>();
    private final Queue<Consumer<Throwable>> excepts = new ConcurrentLinkedDeque<>();
    private final Queue<Runnable> completes = new ConcurrentLinkedDeque<>();

    private T value;
    private Throwable throwable;
    private PromiseStatus status = PromiseStatus.PENDING;

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
     * Registers a callback to be executed when the promise is fulfilled.
     *
     * <p>If the promise is in the {@code PromiseStatus.PENDING} state, the provided callback
     * is queued and will be executed when the promise transitions to {@code PromiseStatus.FULFILLED}.
     * If the promise is already in the {@code PromiseStatus.FULFILLED} state, the callback is executed immediately.</p>
     *
     * @param consumer the callback to handle the fulfilled value
     * @return this promise instance
     * @throws IllegalArgumentException if {@code consumer} is {@code null}
     */
    public Promise<T> then(Consumer<T> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");

        if (this.status == PromiseStatus.PENDING) {
            this.thens.offer(consumer);
        } else if (this.status == PromiseStatus.FULFILLED) {
            consumer.accept(this.value);
        }

        return this;
    }

    /**
     * Registers a callback to be executed when the promise encounters an exception.
     *
     * <p>If the promise is in the {@code PromiseStatus.PENDING} state, the provided callback
     * is queued and will be executed when the promise transitions to {@code PromiseStatus.REJECTED}.
     * If the promise is already in the {@code PromiseStatus.REJECTED} state, the callback is executed immediately.</p>
     *
     * @param consumer the consumer to handle the exception.
     * @return this promise instance.
     * @throws IllegalArgumentException if the {@code consumer} is {@code null}.
     */
    public Promise<T> except(Consumer<Throwable> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");

        if (this.status == PromiseStatus.PENDING) {
            this.excepts.offer(consumer);
        } else if (this.status == PromiseStatus.REJECTED) {
            consumer.accept(this.throwable);
        }

        return this;
    }

    /**
     * Registers a callback to be executed when the promise is completed, regardless of its outcome.
     *
     * <p>If the promise is in the {@code PromiseStatus.PENDING} state, the provided callback
     * is queued and will be executed when the promise is resolved (either fulfilled or rejected).
     * If the promise is already resolved, the callback is executed immediately.</p>
     *
     * @param runnable the callback to execute upon completion
     * @return this promise instance
     * @throws IllegalArgumentException if {@code runnable} is {@code null}
     */
    public Promise<T> complete(Runnable runnable) {
        Validate.notNull(runnable, "runnable cannot be null");

        if (this.status == PromiseStatus.PENDING) {
            this.completes.offer(runnable);
        } else {
            runnable.run();
        }

        return this;
    }

    /**
     * Resolves the promise synchronously on the main server thread.
     *
     * <p>This method executes the resolution logic immediately on the current thread
     * and does not use a Bukkit scheduling method.</p>
     */
    public void resolve() {

        if (this.status != PromiseStatus.PENDING) {
            throw new IllegalStateException("Promise has already been resolved");
        }

        try {
            this.executor.execute(this::onThen, this::onExcept);
        } catch (Exception exception) {
            this.onExcept(exception);
        }

        this.onComplete();
    }

    /**
     * Resolves the promise synchronously in a Bukkit task.
     *
     * <p>This method schedules the resolution logic to run synchronously on the main
     * server thread the next tick using {@link BukkitScheduler#runTask(Plugin, Runnable)}.</p>
     *
     * @param plugin the plugin instance required to schedule the task.
     * @throws IllegalArgumentException if the plugin is {@code null}.
     */
    public void resolveSync(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        Bukkit.getScheduler().runTask(plugin, this::resolve);
    }

    /**
     * Resolves the promise asynchronously in a Bukkit task on a separate thread.
     *
     * <p>This method schedules the resolution logic to run asynchronously on a separate
     * thread using {@link BukkitScheduler#runTaskAsynchronously(Plugin, Runnable)}.</p>
     *
     * @param plugin the plugin instance required to schedule the task.
     * @throws IllegalArgumentException if the plugin is {@code null}.
     */
    public void resolveAsync(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::resolve);
    }

    /**
     * Returns the current status of the promise.
     *
     * @return the current {@code PromiseStatus} of this promise
     */
    public PromiseStatus getStatus() {
        return this.status;
    }

    private void onThen(T value) {
        this.status = PromiseStatus.FULFILLED;
        this.value = value;

        while (!this.thens.isEmpty()) {
            Consumer<T> consumer = this.thens.poll();
            consumer.accept(value);
        }
    }

    private void onExcept(Throwable throwable) {
        this.status = PromiseStatus.REJECTED;
        this.throwable = throwable;

        while (!this.excepts.isEmpty()) {
            Consumer<Throwable> consumer = this.excepts.poll();
            consumer.accept(throwable);
        }
    }

    private void onComplete() {
        while (!this.completes.isEmpty()) {
            Runnable runnable = this.completes.poll();
            runnable.run();
        }
    }

    public enum PromiseStatus {
        PENDING, FULFILLED, REJECTED
    }
}
