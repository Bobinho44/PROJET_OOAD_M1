package fr.univnantes.alma.wrapper;

import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Wrapper of read only mono-valued attribute
 */
public class ReadOnlyMonoValuedAttribute<T> {

    /**
     * Fields
     */
    protected T value;

    /**
     * Creates a new not empty read only mono-valued attribute
     *
     * @param value the initial wrapper value
     */
    public ReadOnlyMonoValuedAttribute(@NonNull T value) {
        Objects.requireNonNull(value, "value cannot be null!");

        this.value = value;
    }

    /**
     * Gets the wrapper value
     *
     * @return the wrapper value
     */
    public @NonNull T get() {
        if (value instanceof Cloneable) {
            try {
                return (T) value.getClass().getMethod("clone").invoke(value, new Object[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return value;
    }

}
