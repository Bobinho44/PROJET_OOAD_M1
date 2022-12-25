package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.commons.territory.TerritoryImpl;

public class MeadowTerritory extends TerritoryImpl {

    /**
     * Creates the meadow territory
     */
    public MeadowTerritory() {
        super(new WoolResource());
    }

}
