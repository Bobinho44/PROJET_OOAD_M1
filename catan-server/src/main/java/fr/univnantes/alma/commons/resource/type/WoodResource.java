package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;

@CardAmount(19)
public class WoodResource extends ResourceImpl {

    /**
     * Creates a new wood resource
     */
    public WoodResource() {
        super("Wood");
    }

}