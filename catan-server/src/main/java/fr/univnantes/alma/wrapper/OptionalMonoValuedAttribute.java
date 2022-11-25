package fr.univnantes.alma.wrapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Wrapper of optional mono-valued attribute
 */
public class OptionalMonoValuedAttribute<T> {

    /**
     * Fields
     */
    private T value;

    /**
     * Creates a new optional mono-valued attribute
     *
     * @param value the initial value
     */
    public OptionalMonoValuedAttribute(@Nullable T value) {
        this.value = value;
    }

    /**
     * Creates a new empty optional mono-valued attribute
     */
    public OptionalMonoValuedAttribute() {
        this(null);
    }

    /**
     * Gets the optional mono-valued attribute
     *
     * @return the optional mono-valued attribute
     */
    public @NonNull Optional<T> get() {
        return Optional.ofNullable(value);
    }

    /**
     * Sets the value of the optional mono-valued attribute
     *
     * @param value the value of the optional mono-valued attribute
     */
    public void set(@Nullable T value) {
        this.value = value;
    }

    /**
     * Unsets the value of the optional mono-valued attribute
     */
    public void unset() {
        this.value = null;
    }

    /**
     * Checks if a value is present in the optional mono-valued attribute
     *
     * @return true if a value is present in the optional mono-valued attribute, false otherwise
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Performs the given action if a value is present in the optional mono-valued attribute
     *
     * @param action the action
     */
    public void ifPresent(@NonNull Consumer<? super T> action) {
        Objects.requireNonNull(action, "action cannot be null!");

        Optional.ofNullable(value).ifPresent(action);
    }

    /**
     * Performs the given action if a value is present in the optional mono-valued attribute, otherwise performs the empty action
     *
     * @param action      the action
     * @param emptyAction the empty action
     */
    public void ifPresentOrElse(@NonNull Consumer<? super T> action, @NonNull Runnable emptyAction) {
        Objects.requireNonNull(action, "action cannot be null!");
        Objects.requireNonNull(emptyAction, "emptyAction cannot be null!");

        Optional.ofNullable(value).ifPresentOrElse(action, emptyAction);
    }

}
