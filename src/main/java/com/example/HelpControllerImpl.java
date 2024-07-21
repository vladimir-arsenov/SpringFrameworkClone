package com.example;

public class HelpControllerImpl implements HelpController {

    private final SupportManager supportManager;

    public HelpControllerImpl(SupportManager supportManager) {
        this.supportManager = supportManager;
    }

    @Override
    public SupportPhrase getSupportPhrase() {
        return supportManager.provideSupport();
    }

    @Override
    public void writeSupportPhrase(SupportPhrase supportPhrase) {
        supportManager.writeSupport(supportPhrase);
    }
}
