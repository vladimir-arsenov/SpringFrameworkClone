package com.example;

import com.example.configuration.Logged;

public interface SupportService {
    @Logged
    SupportPhrase getPhrase();

    void setPhrase(SupportPhrase phrase);
}

