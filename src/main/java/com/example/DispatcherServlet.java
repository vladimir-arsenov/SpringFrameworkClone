package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    private ApplicationContext applicationContext;
    private MappingProvider mappingProvider;
    private HttpCallDispatcher httpCallDispatcher;

    // мы создаём applicationContext в init(), так как по спецификации сервлета
    // tomcat вызывает конструктор по умолчанию, чтобы инициализировать сервлет,
    // то есть конструктор с аргументами просто не вызовется
    // -- это кончено не применимо к этой ситуации (у нас ведь нет аргументов)
    @Override
    public void init() throws ServletException {
        applicationContext = new ApplicationContext("com.example.configuration");
        mappingProvider = new CommonMappingProvider(applicationContext);
        httpCallDispatcher = applicationContext.getInstance(HttpCallDispatcher.class);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var handler = mappingProvider.getMapping(req);
        httpCallDispatcher.dispatch(handler, req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var handler = mappingProvider.getMapping(req);
        httpCallDispatcher.dispatch(handler, req, resp);
    }
}
