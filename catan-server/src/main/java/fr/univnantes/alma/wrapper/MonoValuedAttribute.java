package fr.univnantes.alma.wrapper;

import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Wrapper of mono-valued attribute
 */
public class MonoValuedAttribute<T> extends ReadOnlyMonoValuedAttribute<T> {

    /**
     * Creates a new not empty mono-valued attribute
     *
     * @param value the initial wrapper value
     */
    public MonoValuedAttribute(@NonNull T value) {
        super(value);
    }

    /**
     * Sets the new wrapper value
     *
     * @param value the new wrapper value
     */
    public void set(@NonNull T value) {
        Objects.requireNonNull(value, "value cannot be null!");

        this.value = value;
    }

}
