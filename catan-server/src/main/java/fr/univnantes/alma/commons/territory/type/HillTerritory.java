package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.ClayResource;
import fr.univnantes.alma.commons.territory.Territory;

/**
 * Class representing the territory: hill
 */
public final class HillTerritory extends Territory {

    /**
     * Creates the hill territory
     */
    public HillTerritory() {
        super(new ClayResource());
    }

}
