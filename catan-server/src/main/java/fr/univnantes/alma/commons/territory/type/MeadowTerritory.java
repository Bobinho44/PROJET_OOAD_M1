package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.commons.territory.Territory;

/**
 * Class representing the territory: meadow
 */
public final class MeadowTerritory extends Territory {

    /**
     * Creates the meadow territory
     */
    public MeadowTerritory() {
        super(new WoolResource());
    }

}