package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Implementation of a resource
 */
public abstract class ResourceImpl implements Resource {

    /**
     * Fields
     */
    private final String name;
    private int amount = 1;

    /**
     * Creates a new resource
     *
     * @param name the name
     */
    public ResourceImpl(@NonNull String name) {
        Objects.requireNonNull(name, "name cannot be null!");

        this.name = name;
    }

    /**
     * Sets the amount
     *
     * @param amount the amount
     * @return the resource
     */
    public @NonNull ResourceImpl amount(int amount) {
        this.amount = amount;

        return this;
    }

    /**
     * Gets the resource name
     *
     * @return the resource name
     */
    public @NonNull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseAmount(int amount) {
        this.amount -= amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceImpl)) return false;

        return name.equals(((ResourceImpl) o).getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}