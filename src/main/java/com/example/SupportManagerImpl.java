package com.example;

public class SupportManagerImpl implements SupportManager {

    // Если мы напишем таким образом, то получается, что у нашего класса две функции - создание объекта SupportService
    // и бизнес-логика - что неприемлемо. Так же supportService зависит от реализации SupportServiceImpl.
    // Поэтому мы будем использовать фабрику в классе SupportServiceImpl, при этом мы так же будем иметь зависимость не
    // на конкретную реализацию, а на абстракцию SupportService
    // public SupportService supportService = SupportServiceImpl.getInstance();

    // Заметим что у нас eager singleton - т.е. он инициализируется при создании класса
    // альтернатива lazy - это если бы мы использовали бы SupportServiceFactory.getInstance() в методе  - это плохо.
    // Поэтому отпадает проблемы с многопоточностью - фактори созадаст instance один раз в самом начале, а затем support
    // manager-ы просто берёт этот объект
    // Однако мы до сих пор не избавились от того что этот класс ответсвенен за создание поля через фабрику
    // поэтому поменяем использование фабрики в inline инициализаци на конструктор,
    // при этом создавать объект SupportManager будет ApplicationContext
    public SupportService supportService; /*= SupportServiceFactory.getInstance(); */

    public SupportManagerImpl(SupportService supportService) {
        this.supportService = supportService;
    }

    @Override
    public SupportPhrase provideSupport() {
        return supportService.getPhrase();
    }

    @Override
    public void writeSupport(SupportPhrase supportPhrase) {
        supportService.setPhrase(supportPhrase);
    }
}