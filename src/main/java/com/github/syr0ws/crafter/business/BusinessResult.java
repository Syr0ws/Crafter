package com.github.syr0ws.crafter.business;

import com.github.syr0ws.crafter.util.Validate;

import java.util.Optional;
import java.util.function.Consumer;

public class BusinessResult<T, F extends BusinessFailure> {

    private final T result;
    private final F failure;

    private BusinessResult(T result, F failure) {
        this.result = result;
        this.failure = failure;
    }

    public BusinessResult<T, F> onSuccess(Consumer<T> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");
        this.success().ifPresent(consumer);
        return this;
    }

    public BusinessResult<T, F> onFailure(Consumer<F> consumer) {
        Validate.notNull(consumer, "consumer cannot be null");
        this.failure().ifPresent(consumer);
        return this;
    }

    public Optional<T> success() {
        return Optional.ofNullable(this.result);
    }

    public Optional<F> failure() {
        return Optional.ofNullable(this.failure);
    }

    public static <T, F extends BusinessFailure> BusinessResult<T, F> success(T result) {
        return new BusinessResult<>(result, null);
    }

    public static <T, F extends BusinessFailure> BusinessResult<T, F> error(F failure) {
        Validate.notNull(failure, "failure cannot be null");
        return new BusinessResult<>(null, failure);
    }

    public static <T, F extends BusinessFailure> BusinessResult<T, F> empty() {
        return new BusinessResult<>(null, null);
    }
}
