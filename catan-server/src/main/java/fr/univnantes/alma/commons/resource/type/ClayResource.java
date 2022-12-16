package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.core.ressource.Resource;

@CardAmount(19)
public class ClayResource  extends ResourceImpl {

    /**
     * Creates a new clay resource
     */
    public ClayResource() {
        super("Clay");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource newResource() {
        return new ClayResource();
    }
}