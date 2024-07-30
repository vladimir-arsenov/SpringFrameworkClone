package com.example.business_logic;

import com.example.infrastructure.ioc_container_and_beans.proxy.Logged;

public interface SupportService {
    @Logged
    SupportPhrase getPhrase();

    void setPhrase(SupportPhrase phrase);
}

