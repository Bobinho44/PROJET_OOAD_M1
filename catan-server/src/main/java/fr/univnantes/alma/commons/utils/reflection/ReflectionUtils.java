package fr.univnantes.alma.commons.utils.reflection;

import fr.univnantes.alma.core.exception.EditObjectFieldException;
import fr.univnantes.alma.core.exception.GetObjectFieldException;
import fr.univnantes.alma.core.exception.InstanceCreationException;
import org.reflections.Reflections;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class that handles reflection
 */
public final class ReflectionUtils {

    /**
     * Disabling the constructor (utility class)
     */
    private ReflectionUtils() {}

    /**
     * Gets the subclasses of the selected type in the selected package
     *
     * @param type the type
     * @param path the path of the package
     * @return the subclasses of the selected type in the selected package
     */
    public static @NonNull <T> List<Class<? extends T>> getSubClassesOf(@NonNull Class<T> type, @NonNull String path) {
        Objects.requireNonNull(type, "type cannot be null!");
        Objects.requireNonNull(path, "path cannot be null!");

        return new Reflections(path).getSubTypesOf(type).stream()
                .filter(clazz -> !clazz.isAssignableFrom(type) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()))
                .toList();
    }

    /**
     * Gets the instance of the selected type
     *
     * @param type the type
     * @param parameters the constructor parameters
     * @return the instance of the selected type
     * @throws InstanceCreationException if the object could not be instantiated
     */
    public static @NonNull <T> T getInstancesOf(@NonNull Class<T> type, @Nullable Object... parameters) {
        Objects.requireNonNull(type, "type cannot be null!");

        //Creates an instance of the selected type
        try {
            return type.getDeclaredConstructor(Arrays.stream(Optional.ofNullable(parameters).orElse(new Object[0]))
                            .map(param -> param.getClass().getInterfaces()[0])
                            .toArray(Class<?>[]::new))
                    .newInstance(parameters);
        }

        //The instantiation failed
        catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new InstanceCreationException();
        }
    }

    /**
     * Changes the selected field of the object
     *
     * @param object the object
     * @param fieldName the field name
     * @return the updated object
     * @throws GetObjectFieldException if the selected field of object not exist
     */
    public static @NonNull <T> Object getObjectField(@NonNull T object, @NonNull String fieldName) {
        Objects.requireNonNull(object, "object cannot be null!");
        Objects.requireNonNull(fieldName, "fieldName cannot be null!");

        Class<?> type = object.getClass();

        //Find the Select attribute in the super class and get it
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);

                return field.get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                type = type.getSuperclass();
            }
        }

        //The field could not be got
        throw new GetObjectFieldException();
    }

    /**
     * Changes the selected field of the object
     *
     * @param object the object
     * @param fieldName the field name
     * @param newField the new field
     * @return the updated object
     * @throws EditObjectFieldException if the selected field of object could not be updated
     */
    public static @NonNull <T> T changeObjectField(@NonNull T object, @NonNull String fieldName, @NonNull Object newField) {
        Objects.requireNonNull(object, "object cannot be null!");
        Objects.requireNonNull(fieldName, "fieldName cannot be null!");
        Objects.requireNonNull(newField, "newField cannot be null!");

        Class<?> type = object.getClass();

        //Find the Select attribute in the super class and modify it
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

        //The field could not be updated
        throw new EditObjectFieldException();
    }

}