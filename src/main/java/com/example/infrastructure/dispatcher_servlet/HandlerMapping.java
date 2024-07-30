package com.example.infrastructure.dispatcher_servlet;

import jakarta.servlet.http.HttpServletRequest;

// сопоставляет запрос с объектом обработчиком
public interface HandlerMapping {
    HandlerMappingImpl.Handler getHandler(HttpServletRequest req);
}
