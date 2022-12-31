package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.WoodResource;
import fr.univnantes.alma.commons.territory.Territory;

/**
 * Class representing the territory: forest
 */
public final class ForestTerritory extends Territory {

    /**
     * Creates the forest territory
     */
    public ForestTerritory() {
        super(new WoodResource());
    }

}