package com.example.infrastructure.dispatcher_servlet;

import com.example.infrastructure.ioc_container_and_beans.ApplicationContext;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HandlerMappingImpl implements HandlerMapping {

    private static final String BASE_PATH = "/support";

    private final ApplicationContext applicationContext;
    private final Map<Mapping, Handler> mapping2Handler = new HashMap<>();

    public HandlerMappingImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        init();
    }

    @Override
    public Handler getHandler(HttpServletRequest req) {
        return mapping2Handler.get(new Mapping(req.getPathInfo().substring(BASE_PATH.length()), HttpMethod.valueOf(req.getMethod())));
    }

    private void init() {
        final var controllers = applicationContext.getAllInstance()
                .stream()
                .filter(it -> Arrays.stream(it.getClass().getInterfaces()).anyMatch(i -> i.isAnnotationPresent(Controller.class)))
                .toList();
        for(Object controller : controllers) {
            Arrays.stream(controller.getClass().getInterfaces()[0].getMethods())
                    .filter(it -> it.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> addMapping(controller, method));
        }
    }

    private void addMapping(Object controller, Method method) {
        final var mappingAnnotation = method.getAnnotation(RequestMapping.class);
        mapping2Handler.put(
                new Mapping(mappingAnnotation.path(), mappingAnnotation.method()),
                new Handler(controller, method)
        );
    }
    public record Mapping(
            String path,
            HttpMethod method
    ){}
    public record Handler(
            Object target,
            Method method
    ){}
}
