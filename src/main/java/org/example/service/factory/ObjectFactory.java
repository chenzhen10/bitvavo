package org.example.service.factory;

import org.example.exception.ImplementationNotFoundException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

public enum ObjectFactory {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

    public <T> Optional<T> createObject(Class<T> clazz) {
        T t = null;
        try {
            t = createInstance(clazz);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Error occurred while initializing {}", e.getMessage());
        }
        return Optional.ofNullable(t);
    }

    private <T> T createInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (clazz.isInterface()) {
            Class<? extends T> implementation = findImplementation(clazz).orElseThrow(() -> new ImplementationNotFoundException(clazz));
            return implementation.getDeclaredConstructor().newInstance();
        }
        return clazz.getDeclaredConstructor().newInstance();
    }

    private <T> Optional<Class<? extends T>> findImplementation(Class<T> clazz) {
        String packageName = clazz.getPackage().getName();
        Set<Class<? extends T>> classes = getClassesInPackage(packageName, clazz);
        return classes.stream()
                .filter(cl -> cl.isAssignableFrom(cl) && !cl.isInterface())
                .findFirst(); //in the future we can expand this logic based on some annotations
    }

    private <T> Set<Class<? extends T>> getClassesInPackage(String packageName, Class<T> clazz) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(clazz);
    }
}
