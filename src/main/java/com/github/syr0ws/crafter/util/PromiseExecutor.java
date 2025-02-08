package com.github.syr0ws.crafter.util;

import java.util.function.Consumer;

/**
 * Functional interface representing an executor for a {@link Promise}.
 *
 * @param <T> the type of the result produced by the executor.
 */
@FunctionalInterface
public interface PromiseExecutor<T> {

    /**
     * Executes an operation that can either resolve successfully or reject with an exception.
     *
     * @param resolve the consumer to handle the successful result.
     * @param reject the consumer to handle an exception if one occurs.
     * @throws Exception if an error occurs during execution.
     */
    void execute(Consumer<T> resolve, Consumer<Throwable> reject) throws Exception;
}
