package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.core.ressource.Resource;

@CardAmount(19)
public class WoolResource extends ResourceImpl {

    /**
     * Creates a new wool resource
     */
    public WoolResource() {
        super("Wool");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource newResource() {
        return new WoolResource();
    }

}
