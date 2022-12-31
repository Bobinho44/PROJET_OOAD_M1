package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.territory.Territory;

/**
 * Class representing the territory: field
 */
public final class FieldTerritory extends Territory {

    /**
     * Creates the field territory
     */
    public FieldTerritory() {
        super(new WheatResource());
    }

}