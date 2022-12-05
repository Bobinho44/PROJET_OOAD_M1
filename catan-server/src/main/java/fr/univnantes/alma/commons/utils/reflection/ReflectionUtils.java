package fr.univnantes.alma.commons.utils.reflection;

import org.reflections.Reflections;
import org.springframework.lang.NonNull;

import java.lang.reflect.Modifier;
import java.util.List;

public class ReflectionUtils {

    public static <T> List<Class<? extends T>> getClassesOf(Class<T> type, @NonNull String path) {
        return new Reflections(path).getSubTypesOf(type).stream()
                .filter(clazz -> !clazz.isAssignableFrom(type) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()))
                .toList();
    }

    public static <T> T getInstancesOf(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}