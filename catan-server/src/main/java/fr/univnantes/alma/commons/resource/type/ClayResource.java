package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;

@CardAmount(19)
public class ClayResource  extends ResourceImpl {

    /**
     * Creates a new clay resource
     */
    public ClayResource() {
        super("Clay");
    }

}