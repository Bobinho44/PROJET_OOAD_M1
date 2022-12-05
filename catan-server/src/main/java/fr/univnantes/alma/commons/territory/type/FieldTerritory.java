package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.commons.territory.TerritoryType;

@TerritoryAmount(4)
public class FieldTerritory extends TerritoryType {

    public FieldTerritory() {
        super(new WheatResource());
    }

}