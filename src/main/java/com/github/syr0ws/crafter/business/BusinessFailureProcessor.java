package com.github.syr0ws.crafter.business;

import com.github.syr0ws.crafter.util.Validate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public abstract class BusinessFailureProcessor {

    public void process(BusinessFailure failure) {
        Validate.notNull(failure, "failure cannot be null");
        List<Method> methods = this.findAnnotatedMethods(failure.getClass());
        methods.forEach(method -> this.callHandler(method, failure));
    }

    private List<Method> findAnnotatedMethods(Class<? extends BusinessFailure> type) {
        Method[] methods = this.getClass().getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(BusinessFailureHandler.class))
                .filter(method -> method.getAnnotation(BusinessFailureHandler.class).type().equals(type))
                .toList();
    }

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
