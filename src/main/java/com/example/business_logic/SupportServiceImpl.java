package com.example.business_logic;

// Singleton - некоторые объекты содержат
// много зависимостей, которые бьют по перформансу,
// например, подключение к бд и т.д.
// Поэтому используют паттерн Singleton
public class SupportServiceImpl implements SupportService {

    private SupportPhrase supportPhrase = new SupportPhrase("Hey");

    @Override
    public void setPhrase(SupportPhrase newPhrase) {
        this.supportPhrase =  newPhrase;
    }

    @Override
    public SupportPhrase getPhrase() {
        return supportPhrase;
    }
}
