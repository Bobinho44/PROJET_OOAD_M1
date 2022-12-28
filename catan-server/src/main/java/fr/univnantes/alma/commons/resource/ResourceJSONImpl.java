package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.core.resource.ResourceJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Implementation of a resource JSON information
 */
public class ResourceJSONImpl implements ResourceJSON {

    /**
     * Fields
     */
    private final String name;
    private final int amount;

    /**
     * Creates a new resource json
     *
     * @param name the name
     * @param amount the amount
     */
    public ResourceJSONImpl(@NonNull String name, int amount) {
        Objects.requireNonNull(name, "name cannot be null!");

        this.name = name;
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceJSONImpl that = (ResourceJSONImpl) o;

        return amount == that.amount && Objects.equals(name, that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

}