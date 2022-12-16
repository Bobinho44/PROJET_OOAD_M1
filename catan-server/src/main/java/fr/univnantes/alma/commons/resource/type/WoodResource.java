package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.core.ressource.Resource;

@CardAmount(19)
public class WoodResource extends ResourceImpl {

    /**
     * Creates a new wood resource
     */
    public WoodResource() {
        super("Wood");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource newResource() {
        return new WoodResource();
    }

}