package com.github.syr0ws.crafter.business;

import com.github.syr0ws.crafter.util.Validate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract base class for processing {@link BusinessFailure} instances.

 * <p>This class locates and invokes handler methods annotated with {@link BusinessFailureHandler}
 * that match the type of the failure.</p>
 */
public abstract class BusinessFailureProcessor {

    /**
     * Processes the given business failure by invoking all matching handler methods
     * annotated with {@link BusinessFailureHandler}.
     *
     * @param failure the {@link BusinessFailure} to process
     * @throws NullPointerException if the failure is {@code null}
     * @throws BusinessFailureProcessException if a handler is invalid
     */
    public void process(BusinessFailure failure) {
        Validate.notNull(failure, "failure cannot be null");
        List<Method> methods = this.findAnnotatedMethods(failure.getClass());
        methods.forEach(method -> this.callHandler(method, failure));
    }

    /**
     * Finds all methods in the current class annotated with {@link BusinessFailureHandler}
     * and matching the specified failure type.
     *
     * @param type the failure type to find handler methods for
     * @return a list of matching methods
     */
    private List<Method> findAnnotatedMethods(Class<? extends BusinessFailure> type) {
        Method[] methods = this.getClass().getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(BusinessFailureHandler.class))
                .filter(method -> method.getAnnotation(BusinessFailureHandler.class).type().equals(type))
                .toList();
    }

    /**
     * Invokes the specified handler method with the provided failure instance.
     *
     * @param method the method to invoke
     * @param failure the failure instance to pass to the method
     * @throws BusinessFailureProcessException if the method is not valid or cannot be invoked
     */
    private void callHandler(Method method, BusinessFailure failure) {

        if (method.getParameterCount() != 1) {
            throw new BusinessFailureProcessException(String.format("Method %s must have only one parameter", method.getName()));
        }

        Parameter[] parameters = method.getParameters();

        if (!parameters[0].getType().equals(failure.getClass())) {
            throw new BusinessFailureProcessException(String.format("The first parameter of the method %s must be of type %s", method.getName(), failure.getClass().getName()));
        }

        try {
            method.invoke(this, failure);
        } catch (ReflectiveOperationException exception) {
            throw new BusinessFailureProcessException(String.format("Could not invoke method %s on %s", method.getName(), failure.getClass().getName()), exception);
        }
    }
}
