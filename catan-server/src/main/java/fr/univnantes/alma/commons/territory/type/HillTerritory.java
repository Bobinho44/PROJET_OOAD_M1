package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.resource.type.ClayResource;
import fr.univnantes.alma.commons.territory.TerritoryType;

@TerritoryAmount(3)
public class HillTerritory extends TerritoryType {

    public HillTerritory() {
        super(new ClayResource());
    }

}
