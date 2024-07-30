package com.example.infrastructure;

import com.example.infrastructure.Configuration;
import com.example.infrastructure.Instance;
import com.example.infrastructure.ProxyApplier;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

public class ApplicationContext {
    private final Map<Class<?>, Object> configInstances = new HashMap<>();  // объекты конфигураций
    private final List<Method> instanceMethods = new ArrayList<>(); // методы, которые создают бины (помечены @Instance)
    private final Map<String, Object> instances = new HashMap<>(); // по факту хранятся bean-ы Spring-а
    private final Set<String> instancesInProgress = new HashSet<>(); // бины которые в процессе создания

    public ApplicationContext(String packageName) {
        scanConfigurationClasses(packageName);
        init();
    }

    /**
     * Этот метод достаёт все классы конфигурации (помеченные @Configuration),
     * затем создаёт инстансы этих классов и кладёт их в configInstances,
     * в каждом инстансе все методы помеченные @Instance кладутся в instanceMethods
     */
    private void scanConfigurationClasses(String packageName) {
        Reflections reflections = new Reflections("com.example.configuration");
        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);
        for (Class<?> configClass : configurationClasses) {
            try {
                Object configInstance = configClass.getDeclaredConstructor().newInstance();
                configInstances.put(configClass, configInstance);
                for (Method method : configClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Instance.class)) {
                        instanceMethods.add(method);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Сначала сортирует методы, которые создают бины,
     * по приоритету, указанному в @Instance(priority);
     * затем инициализирует эти бины
     */
    private void init() {
        instanceMethods.stream()
                .sorted(Comparator.comparingInt((Method method) -> method.getAnnotation(Instance.class).priority()).reversed())
                .forEach(method -> createInstance(method.getName(), method));
    }

    /**
     * Получает все прокси (бины которые наследуются от ProxyApplier)
     * и оборачивает ими объект
     */
    private  Object applyProxies(Object object) {
        Object result = object;
        for(ProxyApplier applier : getInstances(ProxyApplier.class)) {
            result = applier.apply(result);
        }
        return result;
    }

    /**
     * Либо возвращает instanceType (или его базового класса) бин из instances,
     * либо создаёт новый через метод createInstanceByType
     */
    public <T> T getInstance(Class<T> instanceType) {
        return instanceType.cast(instances.values().stream()
                .filter(instanceType::isInstance)
                .findFirst()
                .orElseGet(() -> createInstanceByType(instanceType)));
    }

    public List<Object> getAllInstance() {
        return instances.values().stream().toList();
    }

    /**
     * Возвращает бины instanceType (или его базового класса) из instances
     */
    public <T> List<T> getInstances(Class<T> instanceType) {
        return (List<T>) instances.values().stream()
                .filter(instanceType::isInstance)
                .toList();
    }

    /**
     * Пробегается по методам создающим бины, и,
     * найдя метод, который создаёт бин типа instanceType, создаёт бин;
     * иначе (если такого метода нет) выбрасывает исключение
     */
    private Object createInstanceByType(Class<?> instanceType) {
        for(Method method : instanceMethods) {
            if (instanceType.isAssignableFrom(method.getReturnType())) {
                return createInstance(method.getName(), method);
            }
        }
        throw new RuntimeException("No instance found for type" + instanceType.getName());
    }

    /**
     * Сначала получает класс, который содержит method, затем
     * получает массив зависимостей будущего бина и cоздаёт бин.
     * <p>
     * В начале метода instanceName добавляется в instancesInProgress, что
     * позволит поймать циклическую зависимость, если такая существует,
     * это произойдёт следующим образом:
     * resolveDependencies -> getInstance -> createInstanceByType -> createInstance
     * в этом случае сработает первый блок if и выбросится исключение
     */
    private Object createInstance(String instanceName, Method method) {
        if (instancesInProgress.contains(instanceName)) {
            throw new RuntimeException("Circular dependency detected for instance: " + instanceName);
        }
        instancesInProgress.add(instanceName);
        try {
            Object configInstance = configInstances.get(method.getDeclaringClass());
            Object[] dependencies = resolveDependencies(method);
            Object instance = method.invoke(configInstance, dependencies);
            instances.put(instanceName, applyProxies(instance));
            return instance;
        } catch(Exception e) {
            throw new RuntimeException("Failed to create instance: " + instanceName, e);
        } finally {
            instancesInProgress.remove(instanceName);
        }
    }

    /**
     * Возвращает массив зависимостей (аргументы метода, который создаёт бин)
     */
    private Object[] resolveDependencies(Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(this::getInstance)
                .toArray();
    }

//    private Object wrapWithLoggingProxy(Object object) {
//        return Proxy.newProxyInstance(
//                this.getClass().getClassLoader(),
//                object.getClass().getInterfaces(),
//                new LoggingInvocationHandler(object)
//        );
//    }


}
//        for(Object configuration : configurations) {
//            // getDeclaredMethods - аозвращает только собственные методы
//            List<Method> methods = Arrays.stream(configuration.getClass().getMethods())
//                    .filter(method -> method.isAnnotationPresent(Instance.class))
//                    .toList();
//
//
//            List<Method> methodsWithoutParams = methods.stream()
//                    .filter(method -> method.getParameters().length == 0)
//                    .toList();
//            List<Method> methodsWithParams = methods.stream()
//                    .filter(method -> method.getParameters().length > 0)
//                    .toList();
//            // сначала заполняем бины без параметров
//            for (Method method : methodsWithoutParams) {
//                final var instance = wrapWithLoggingProxy(method.invoke(configuration));
//                instances.put(method.getReturnType(), instance);
//            }
//            // затем заполняем бинами из мапы бины с параметрами
//            for (Method method : methodsWithParams) {
//                Object[] objects = Arrays.stream(method.getParameters())
//                        .map(param -> instances.get(param.getType()))
//                        .toArray();
//                final var instance = wrapWithLoggingProxy(method.invoke(configuration, objects));
//                instances.put(method.getReturnType(), instance);
//            }