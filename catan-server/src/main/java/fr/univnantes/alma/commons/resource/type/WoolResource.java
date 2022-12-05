package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;

@CardAmount(19)
public class WoolResource extends ResourceImpl {

    /**
     * Creates a new wool resource
     */
    public WoolResource() {
        super("Wool");
    }

}
