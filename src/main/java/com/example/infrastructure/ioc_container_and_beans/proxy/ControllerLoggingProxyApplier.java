package com.example.infrastructure.ioc_container_and_beans.proxy;

import com.example.infrastructure.dispatcher_servlet.Controller;

import java.lang.reflect.Proxy;
import java.util.Arrays;


public class ControllerLoggingProxyApplier implements ProxyApplier {
    @Override
    public Object apply(Object object) {
        final var shouldProxyBeApplied = Arrays.stream(object.getClass().getInterfaces())
                .anyMatch(it -> it.isAnnotationPresent(Controller.class));

        if(shouldProxyBeApplied) {
            return Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    object.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        if (method.isAnnotationPresent(Logged.class)) {
                            System.out.println("Method start");
                            final var result = method.invoke(object, args);
                            System.out.println("Method end");
                            return result;
                        } else {
                            return method.invoke(object, args);
                        }
                    }
            );
        } else {
            return object;
        }
    }
}
