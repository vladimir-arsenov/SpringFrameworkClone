package com.example.business_logic;

import com.example.infrastructure.Logged;

public interface SupportService {
    @Logged
    SupportPhrase getPhrase();

    void setPhrase(SupportPhrase phrase);
}

