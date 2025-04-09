package com.github.syr0ws.crafter.business;

import com.github.syr0ws.crafter.util.Validate;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents the result of a business operation, encapsulating either a success result or a failure.
 *
 * @param <T> the type of the object returned on success
 * @param <F> the type of the error returned on failure, must extend {@link BusinessFailure}
 */
public class BusinessResult<T, F extends BusinessFailure> {

    private final T result;
    private final F failure;

    private BusinessResult(T result, F failure) {
        this.result = result;
        this.failure = failure;
    }

    /**
     * Executes the given consumer if the result is successful.
     *
     * @param consumer the consumer to be executed if a successful result is present
     * @return this {@code BusinessResult} instance
     * @throws NullPointerException if the consumer is {@code null}
     */
    public BusinessResult<T, F> onSuccess(Consumer<T> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");
        this.success().ifPresent(consumer);
        return this;
    }

    /**
     * Executes the given consumer if a failure is present.
     *
     * @param consumer the consumer to be executed if a failure is present
     * @return this {@code BusinessResult} instance
     * @throws NullPointerException if the consumer is {@code null}
     */
    public BusinessResult<T, F> onFailure(Consumer<F> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");
        this.failure().ifPresent(consumer);
        return this;
    }

    /**
     * Returns the result value if the operation was successful.
     *
     * @return an {@link Optional} containing the result if present and no failure occurred;
     *         otherwise, an empty {@code Optional}
     */
    public Optional<T> success() {
        return Optional.ofNullable(this.result);
    }

    /**
     * Returns the failure value if the operation was unsuccessful.
     *
     * @return an {@link Optional} containing the failure if present and no success result exists;
     *         otherwise, an empty {@code Optional}
     */
    public Optional<F> failure() {
        return Optional.ofNullable(this.failure);
    }

    /**
     * Creates a successful {@code BusinessResult}.
     *
     * @param result the result value
     * @param <T> the type of the result
     * @param <F> the type of the failure
     * @return a {@code BusinessResult} representing success
     */
    public static <T, F extends BusinessFailure> BusinessResult<T, F> success(T result) {
        return new BusinessResult<>(result, null);
    }

    /**
     * Creates a failed {@code BusinessResult}.
     *
     * @param failure the failure value
     * @param <T> the type of the result
     * @param <F> the type of the failure
     * @return a {@code BusinessResult} representing failure
     * @throws NullPointerException if {@code failure} is {@code null}
     */
    public static <T, F extends BusinessFailure> BusinessResult<T, F> error(F failure) {
        Validate.notNull(failure, "failure cannot be null");
        return new BusinessResult<>(null, failure);
    }

    /**
     * Creates an empty {@code BusinessResult} with neither a success nor a failure.
     *
     * @param <T> the type of the result
     * @param <F> the type of the failure
     * @return an empty {@code BusinessResult}
     */
    public static <T, F extends BusinessFailure> BusinessResult<T, F> empty() {
        return new BusinessResult<>(null, null);
    }
}
