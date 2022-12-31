package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.core.resource.IResourceJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Implementation of a resource JSON information
 *
 * @param name Fields
 */
public record ResourceJSON(String name, int amount) implements IResourceJSON {

    /**
     * Creates a new resource json
     *
     * @param name   the name
     * @param amount the amount
     */
    public ResourceJSON(@NonNull String name, int amount) {
        Objects.requireNonNull(name, "name cannot be null!");

        this.name = name;
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String name() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int amount() {
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceJSON resourceJSON)) return false;
        return amount == resourceJSON.amount && Objects.equals(name, resourceJSON.name);
    }

}