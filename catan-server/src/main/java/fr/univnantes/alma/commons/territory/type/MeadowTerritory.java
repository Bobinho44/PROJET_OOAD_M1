package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.resource.type.WoolResource;
import fr.univnantes.alma.commons.territory.TerritoryType;

@TerritoryAmount(4)
public class MeadowTerritory extends TerritoryType {

    public MeadowTerritory() {
        super(new WoolResource());
    }

}
