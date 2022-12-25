package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.territory.TerritoryImpl;

public class MountainTerritory extends TerritoryImpl {

    /**
     * Creates the mountain territory
     */
    public MountainTerritory() {
        super(new OreResource());
    }

}
