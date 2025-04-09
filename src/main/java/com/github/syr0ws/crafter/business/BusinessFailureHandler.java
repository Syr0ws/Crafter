package com.github.syr0ws.crafter.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as a handler for a specific type of {@link BusinessFailure}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BusinessFailureHandler {

    /**
     * Specifies the type of {@link BusinessFailure} this method handles.
     *
     * @return the class type of the business failure
     */
    Class<? extends BusinessFailure> type();
}
