package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.resource.type.ClayResource;
import fr.univnantes.alma.core.territory.type.TerritoryType;

@TerritoryAmount(3)
public class HillTerritory extends TerritoryType {

    /**
     * Creates the hill territory
     */
    public HillTerritory() {
        super(new ClayResource());
    }

}
