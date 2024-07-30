package com.example.business_logic;

import com.example.infrastructure.dispatcher_servlet.Controller;
import com.example.infrastructure.dispatcher_servlet.HttpMethod;
import com.example.infrastructure.ioc_container_and_beans.proxy.Logged;
import com.example.infrastructure.dispatcher_servlet.RequestMapping;

@Controller
public interface HelpController {

    @Logged
    @RequestMapping(method = HttpMethod.GET)
    SupportPhrase getSupportPhrase();

    @Logged
    @RequestMapping(method = HttpMethod.POST)
    void writeSupportPhrase(SupportPhrase supportPhrase);
}
