package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;
import fr.univnantes.alma.core.ressource.Resource;

@CardAmount(19)
public class WheatResource  extends ResourceImpl {

    /**
     * Creates a new wheat resource
     */
    public WheatResource() {
        super("Wheat");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource newResource() {
        return new WheatResource();
    }

}
