package com.example.infrastructure.ioc_container_and_beans.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler  implements InvocationHandler {

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Logged.class)) {
            System.out.println("Method start");
            final var result = method.invoke(target, args);
            System.out.println("Method end");
            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}
