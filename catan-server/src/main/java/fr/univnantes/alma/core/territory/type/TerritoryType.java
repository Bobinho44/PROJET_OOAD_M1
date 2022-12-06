package fr.univnantes.alma.core.territory.type;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.Nullable;

public abstract class TerritoryType {

    /**
     * Fields
     */
    private final Resource associatedResource;

    /**
     * Creates a new territory type
     *
     * @param associatedResource the associated resource
     */
    public TerritoryType(@Nullable Resource associatedResource) {
        this.associatedResource = associatedResource;
    }

    /**
     * Gets the associated resource
     *
     * @return the associated resource
     */
    public @Nullable Resource getAssociatedResource() {
        return associatedResource;
    }

}