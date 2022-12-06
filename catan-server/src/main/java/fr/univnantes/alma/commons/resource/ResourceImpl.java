package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Enum representing resource
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
     * Gets the resource name
     *
     * @return the resource name
     */
    public @NonNull String getName() {
        return name;
    }


    public @NonNull Resource amount(int amount) {
        this.amount = amount;

        return this;
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
    public boolean isSimilar(@NonNull Resource resource) {
        return name.equals(resource.getName());
    }

}