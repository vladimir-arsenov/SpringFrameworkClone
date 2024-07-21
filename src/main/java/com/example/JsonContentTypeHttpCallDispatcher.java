package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

public class JsonContentTypeHttpCallDispatcher implements HttpCallDispatcher {

    private final ObjectMapper objectMapper;

    public JsonContentTypeHttpCallDispatcher(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void dispatch(CommonMappingProvider.Handler handler, HttpServletRequest request, HttpServletResponse resp) {
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
