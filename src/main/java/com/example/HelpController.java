package com.example;

import com.example.configuration.Logged;

@Controller
public interface HelpController {

    @Logged
    @RequestMapping(method = HttpMethod.GET)
    SupportPhrase getSupportPhrase();

    @Logged
    @RequestMapping(method = HttpMethod.POST)
    void writeSupportPhrase(SupportPhrase supportPhrase);
}
