package fr.univnantes.alma.commons.resource;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Enum representing resource
 */
public enum ResourceImpl implements Resource {
    WOOD("Wood"),
    WOOL("Wool"),
    WHEAT("Wheat"),
    CLAY("Clay"),
    ORE("Ore"),
    NONE("None");

    /**
     * Fields
     */
    private final String name;

    /**
     * Creates a new Resource
     *
     * @param name the name
     */
    ResourceImpl(@NonNull String name) {
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
