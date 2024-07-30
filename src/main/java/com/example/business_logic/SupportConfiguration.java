package com.example.business_logic;

import com.example.infrastructure.dispatcher_servlet.HandlerAdapter;
import com.example.infrastructure.dispatcher_servlet.JsonContentTypeHandlerAdapter;
import com.example.infrastructure.ioc_container_and_beans.Bean;
import com.example.infrastructure.ioc_container_and_beans.Configuration;
import com.example.infrastructure.ioc_container_and_beans.proxy.ControllerLoggingProxyApplier;
import com.example.infrastructure.ioc_container_and_beans.proxy.ProxyApplier;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SupportConfiguration {

    @Bean(priority = Integer.MAX_VALUE)
    public ProxyApplier controllerLoggingProxy() {return new ControllerLoggingProxyApplier();}

    @Bean
    public SupportManager supportManager() {
        return new SupportManagerImpl(supportService());
    }

    @Bean
    public SupportService supportService() {
        return new SupportServiceImpl();
    }

    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Bean
    public HandlerAdapter httpCallDispatcher(ObjectMapper objectMapper) {
        return new JsonContentTypeHandlerAdapter(objectMapper);
    }

    @Bean
    public HelpController helpController(SupportManager supportManager) {
        return new HelpControllerImpl(supportManager);
    }
}
