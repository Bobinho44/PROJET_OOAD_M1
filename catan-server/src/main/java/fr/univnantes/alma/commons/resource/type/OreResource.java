package fr.univnantes.alma.commons.resource.type;

import fr.univnantes.alma.commons.annotation.CardAmount;
import fr.univnantes.alma.commons.resource.ResourceImpl;

@CardAmount(19)
public class OreResource  extends ResourceImpl {

    /**
     * Creates a new ore resource
     */
    public OreResource() {
        super("Ore");
    }

}