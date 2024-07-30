package com.example.infrastructure.dispatcher_servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.stream.Collectors;

public class JsonContentTypeHandlerAdapter implements HandlerAdapter {

    private final ObjectMapper objectMapper;

    public JsonContentTypeHandlerAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HandlerMappingImpl.Handler handler, HttpServletRequest request, HttpServletResponse resp) {
        final Object result;
        resp.setContentType("application/json");
        try {
            if (handler.method().getParameters().length == 0) {
                result = handler.method().invoke(handler.target());
                resp.getWriter().append(objectMapper.writeValueAsString(result));
            } else {
                final var paramType = handler.method().getParameters()[0].getType();
                String content = request.getReader().lines().collect(Collectors.joining());
                handler.method().invoke(handler.target(), objectMapper.readValue(content, paramType));
                resp.setStatus(201);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
