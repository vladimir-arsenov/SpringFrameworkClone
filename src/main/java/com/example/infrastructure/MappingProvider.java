package com.example.infrastructure;

import jakarta.servlet.http.HttpServletRequest;

public interface MappingProvider {
    CommonMappingProvider.Handler getMapping(HttpServletRequest req);
}
