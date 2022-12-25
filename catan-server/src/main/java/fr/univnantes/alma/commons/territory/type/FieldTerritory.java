package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.territory.TerritoryImpl;

public class FieldTerritory extends TerritoryImpl {

    /**
     * Creates the field territory
     */
    public FieldTerritory() {
        super(new WheatResource());
    }

}