package fr.univnantes.alma.wrapper;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Wrapper of multivalued attribute
 */
public class MultiValuedAttribute<T> {

    /**
     * Fields
     */
    private final List<T> values;

    /**
     * Creates a new multivalued attribute
     *
     * @param values the initial values
     */
    public MultiValuedAttribute(@NonNull List<T> values) {
        this.values = values;
    }

    /**
     * Creates a new empty multivalued attribute
     */
    public MultiValuedAttribute() {
        this(new ArrayList<>());
    }

    /**
     * Gets a copy of the multivalued attribute
     *
     * @return the copy of the multivalued attribute
     */
    public @NonNull List<T> get() {
        return List.copyOf(values);
    }

    /**
     * Gets a stream of the multivalued attribute
     *
     * @return the stream of the multivalued attribute
     */
    public @NonNull Stream<T> stream() {
        return values.stream();
    }

    /**
     * Gets the value at index i of the multivalued attribute
     *
     * @param i the index of the value
     * @return the value at index i of the multivalued attribute
     */
    public @NonNull T get(int i) {
        return values.get(i);
    }

    /**
     * Adds a new value to the multivalued attribute
     *
     * @param value the value
     */
    public void add(@NonNull T value) {
        Objects.requireNonNull(value, "value cannot be null!");

        values.add(value);
    }

    /**
     * Removes a value from the multivalued attribute
     *
     * @param value the value
     */
    public void remove(@NonNull T value) {
        Objects.requireNonNull(value, "value cannot be null!");

        values.remove(value);
    }

    /**
     * Removes values from the multivalued attribute
     *
     * @param values the values
     */
    public void removeAll(@NonNull List<T> values) {
        Objects.requireNonNull(values, "values cannot be null!");

        this.values.removeAll(values);
    }

    /**
     * Removes index-th value from the multivalued attribute
     *
     * @param index the index
     */
    public void remove(int index) {
        values.remove(index);
    }

    public void clear() {
        values.clear();
    }

    /**
     * Gets the list size
     *
     * @return the list size
     */
    public int size() {
        return values.size();
    }

    /**
     * Checks if the multivalued attribute contains an element
     *
     * @param element the element
     * @return true if the multivalued attribute contains an element, false otherwise
     */
    public boolean contains(@NonNull T element) {
        Objects.requireNonNull(element, "element cannot be null!");

        return values.contains(element);
    }

}