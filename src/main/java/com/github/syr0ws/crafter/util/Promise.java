package com.github.syr0ws.crafter.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represents a promise-like mechanism for handling asynchronous operations.
 *
 * @param <T> the type of the result produced by the promise.
 */
public class Promise<T> {

    private final PromiseExecutor<T> executor;
    private final CompletableFuture<T> future;

    /**
     * Constructs a new {@code Promise} with the specified executor.
     *
     * @param executor the executor responsible for resolving the promise.
     * @throws IllegalArgumentException if the executor is {@code null}.
     */
    public Promise(PromiseExecutor<T> executor) {
        this(executor, new CompletableFuture<>());
    }

    private Promise(PromiseExecutor<T> executor, CompletableFuture<T> future) {
        Validate.notNull(executor, "executor cannot be null");
        Validate.notNull(future, "future cannot be null");

        this.executor = executor;
        this.future = future;
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
        this.future.thenAccept(consumer);
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
        this.future.exceptionally(throwable -> {
            consumer.accept(throwable);
            return null;
        });
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
        this.future.whenComplete((result, throwable) -> runnable.run());
        return this;
    }

    /**
     * Resolves the promise synchronously on the main server thread.
     */
    public void resolve() {
        this.execute();
    }

    /**
     * Resolves the promise synchronously on the main server thread in a task.
     *
     * @param plugin the plugin instance required to schedule the task.
     * @throws IllegalArgumentException if the plugin is {@code null}.
     */
    public void resolveSync(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        Bukkit.getScheduler().runTask(plugin, this::execute);
    }

    /**
     * Resolves the promise asynchronously on a separate thread in a task.
     *
     * @param plugin the plugin instance required to schedule the task.
     * @throws IllegalArgumentException if the plugin is {@code null}.
     */
    public void resolveAsync(Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::execute);
    }

    private void onThen(T value) {
        this.future.complete(value);
    }

    private void onExcept(Throwable throwable) {
        this.future.completeExceptionally(throwable);
    }

    private void execute() {
        try {
            this.executor.execute(this::onThen, this::onExcept);
        } catch (Exception exception) {
            this.onExcept(exception);
        }
    }

    public static Promise<Object[]> all(Promise<?>... promises) {
        Validate.notNull(promises, "promises cannot be null");

        CompletableFuture<?>[] futures = Stream.of(promises)
                .map(promise -> promise.future)
                .toArray(CompletableFuture<?>[]::new);

        CompletableFuture<Object[]> future = CompletableFuture.allOf(futures)
                .thenApply(__ -> Arrays.stream(futures).map(CompletableFuture::join).toArray(Object[]::new));

        return new Promise<>((resolve, reject) -> {
            System.out.println("hello");
            future.thenAccept(resolve).exceptionally(ex -> {
                reject.accept(ex);
                return null;
            }).get();
            System.out.println("test");
        });
    }
}
