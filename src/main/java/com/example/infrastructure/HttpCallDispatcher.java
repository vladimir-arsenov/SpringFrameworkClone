package com.example.infrastructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpCallDispatcher {
    void dispatch(CommonMappingProvider.Handler handler, HttpServletRequest request, HttpServletResponse resp);
}
