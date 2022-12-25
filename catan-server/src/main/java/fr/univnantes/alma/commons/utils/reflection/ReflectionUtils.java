package fr.univnantes.alma.commons.utils.reflection;

import org.reflections.Reflections;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static <T> List<Class<? extends T>> getClassesOf(@NonNull Class<T> type, @NonNull String path) {
        return new Reflections(path).getSubTypesOf(type).stream()
                .filter(clazz -> !clazz.isAssignableFrom(type) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()))
                .toList();
    }

    public static <T> T getInstancesOf(@NonNull Class<T> type, Object... params) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor(Arrays.stream(params).map(Object::getClass).toArray(Class<?>[]::new));

            return constructor.newInstance(params);

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, T extends S> T changeObjectField(@NonNull T object, @NonNull String fieldName, @NonNull Object newField) {
        Class<?> type = object.getClass();

        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, newField);

                return object;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                type = type.getSuperclass();
            }
        }
        throw new RuntimeException();
    }

}