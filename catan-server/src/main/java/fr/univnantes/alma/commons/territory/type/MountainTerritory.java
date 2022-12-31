package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.territory.Territory;

/**
 * Class representing the territory: mountain
 */
public final class MountainTerritory extends Territory {

    /**
     * Creates the mountain territory
     */
    public MountainTerritory() {
        super(new OreResource());
    }

}
