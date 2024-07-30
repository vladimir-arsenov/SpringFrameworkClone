package com.example.infrastructure.dispatcher_servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// ответственен за обработку Http запроса
public interface HandlerAdapter {
    void handle(HandlerMappingImpl.Handler handler, HttpServletRequest request, HttpServletResponse resp);
}
