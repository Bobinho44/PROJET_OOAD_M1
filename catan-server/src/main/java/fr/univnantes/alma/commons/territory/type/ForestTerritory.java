package fr.univnantes.alma.commons.territory.type;

import fr.univnantes.alma.commons.annotation.TerritoryAmount;
import fr.univnantes.alma.commons.resource.type.WoodResource;
import fr.univnantes.alma.commons.territory.TerritoryType;

@TerritoryAmount(4)
public class ForestTerritory extends TerritoryType {

    public ForestTerritory() {
        super(new WoodResource());
    }

}