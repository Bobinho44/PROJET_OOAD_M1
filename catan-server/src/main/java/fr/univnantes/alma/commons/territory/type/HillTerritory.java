package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.ClayResource;
import fr.univnantes.alma.commons.territory.TerritoryImpl;

public class HillTerritory extends TerritoryImpl {

    /**
     * Creates the hill territory
     */
    public HillTerritory() {
        super(new ClayResource());
    }

}
