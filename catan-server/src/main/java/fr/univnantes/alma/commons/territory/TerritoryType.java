package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.Nullable;

public abstract class TerritoryType {

    private final Resource associatedResource;

    public TerritoryType(@Nullable Resource associatedResource) {
        this.associatedResource = associatedResource;
    }

    public @Nullable Resource getAssociatedResource() {
        return associatedResource;
    }

}