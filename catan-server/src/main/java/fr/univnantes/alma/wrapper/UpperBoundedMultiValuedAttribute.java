package fr.univnantes.alma.wrapper;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Wrapper of upper bounded multivalued attribute
 */
public class UpperBoundedMultiValuedAttribute<T> extends MultiValuedAttribute<T> {

    /**
     * Fields
     */
    private int maxSize;

    /**
     * Creates a new upper bounder multivalued attribute wrapper
     *
     * @param maxSize the upper bound
     * @param values  the initial values
     */
    public UpperBoundedMultiValuedAttribute(int maxSize, List<T> values) {
        super(values);
        this.maxSize = maxSize;
    }

    /**
     * Creates a new empty upper bounder multivalued attribute wrapper
     *
     * @param maxSize the upper bound
     */
    public UpperBoundedMultiValuedAttribute(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    /**
     * Adds the value to the list (within the limit of maxSize element)
     *
     * @param value the added value
     */
    @Override
    public void add(@NonNull T value) {
        if (isFull()) {
            throw new RuntimeException("The upperBoundedMultiValuedAttribute is full!");
        }

        super.add(value);
    }

    /**
     * Gets the max size
     *
     * @return the max size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * redefines the list max size
     *
     * @param maxSize the max size
     */
    public void resize(int maxSize) {
        this.maxSize = maxSize;

        while (isFull()) {
            super.remove(size() - 1);
        }
    }

    /**
     * Checks if the list is full
     *
     * @return true if the list is full, false otherwise
     */
    public boolean isFull() {
        return super.size() >= maxSize;
    }

}
