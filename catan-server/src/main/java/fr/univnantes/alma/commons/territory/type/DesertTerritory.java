package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.core.territory.type.TerritoryType;

@TerritoryAmount(1)
public class DesertTerritory extends TerritoryType {

    /**
     * Creates the desert territory
     */
    public DesertTerritory() {
        super(null);
    }

}