package fr.univnantes.alma.commons.utils.reflection;

import org.reflections.Reflections;
import org.springframework.lang.NonNull;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReflectionUtils {

    public static <T> List<Class<? extends T>> getClassesOf(Class<T> type, @NonNull String path) {
        return new Reflections(path).getSubTypesOf(type).stream()
                .filter(clazz -> !clazz.isAssignableFrom(type) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()))
                .toList();
    }

    public static <T> T getInstancesOf(Class<T> type, Objects... args) {
        try {
            return type.getDeclaredConstructor((Class<?>[]) Arrays.stream(args).map(Objects::getClass).toArray()).newInstance(Arrays.stream(args).toArray());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}