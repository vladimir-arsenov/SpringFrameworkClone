package com.example.business_logic;

import com.example.business_logic.SupportPhrase;
import com.example.infrastructure.Controller;
import com.example.infrastructure.HttpMethod;
import com.example.infrastructure.Logged;
import com.example.infrastructure.RequestMapping;

@Controller
public interface HelpController {

    @Logged
    @RequestMapping(method = HttpMethod.GET)
    SupportPhrase getSupportPhrase();

    @Logged
    @RequestMapping(method = HttpMethod.POST)
    void writeSupportPhrase(SupportPhrase supportPhrase);
}
