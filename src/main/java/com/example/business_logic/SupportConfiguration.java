package com.example.business_logic;

import com.example.infrastructure.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SupportConfiguration {

    @Instance(priority = Integer.MAX_VALUE)
    public ProxyApplier controllerLoggingProxy() {return new ControllerLoggingProxyApplier();}

    @Instance
    public SupportManager supportManager() {
        return new SupportManagerImpl(supportService());
    }

    @Instance
    public SupportService supportService() {
        return new SupportServiceImpl();
    }

    @Instance
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Instance
    public HttpCallDispatcher httpCallDispatcher(ObjectMapper objectMapper) {
        return new JsonContentTypeHttpCallDispatcher(objectMapper);
    }

    @Instance
    public HelpController helpController(SupportManager supportManager) {
        return new HelpControllerImpl(supportManager);
    }
}
