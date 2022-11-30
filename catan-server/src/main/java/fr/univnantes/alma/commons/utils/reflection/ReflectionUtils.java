package fr.univnantes.alma.commons.utils.reflection;

import fr.univnantes.alma.core.card.type.SpecialCard;
import org.reflections.Reflections;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.List;

public class ReflectionUtils {
    public static <T> List<? extends T> getInstancesOf(Class<? extends T> type, @NonNull String path) {
        return new Reflections(path).getSubTypesOf(type).stream()
                .map(clazz -> {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                })
                .toList();
    }

}