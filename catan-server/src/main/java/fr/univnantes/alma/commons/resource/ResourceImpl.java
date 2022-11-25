package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.Objects;

public abstract class ResourceImpl implements Resource {

    /**
     * Fields
     */
    private final String name;

    /**
     * Creates a new Resource
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

}
