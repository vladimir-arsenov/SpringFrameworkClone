package com.example.business_logic;

public class SupportServiceFactory {

    private static final SupportService INSTANCE = init();

    public static SupportService getInstance() {
        return INSTANCE;
    }

    private static SupportService init() {
        // дополнительные настройки
        return new SupportServiceImpl();
    }



    // Так было изначально (но теперь мы хотим сразу создавать объект):

//    // Так как каждый раз когда мы входим в функцию getInstance() мы дублируем копию ссылки INSTANCE в стек
//    // в это время INSTANCE может изменить другой поток.
//    // Поэтому мы используем volatile, чтобы INSTANCE брался прямо из heap-а
//    private static volatile SupportServiceImpl INSTANCE;
//
//    // synchronized - выстраивает потоки в очередь и они по одному забирают
//    // объект. Монитор в данном случае это объект класса SupportService
//    // Однако блокировать метод это очень дорого с точки зрения перформанса
//    public static /*synchronized*/ SupportService getInstance() {
//        if (INSTANCE == null) {
//            synchronized (SupportServiceImpl.class) { // критическая секция
//                if (INSTANCE == null) { // проверяем, проинициализировал ли кто INSTANCE в то время как мы захватывали критическую секцию
//                    INSTANCE = new SupportServiceImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
