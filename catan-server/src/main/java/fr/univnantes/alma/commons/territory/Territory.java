package fr.univnantes.alma.commons.territory;

import java.util.ArrayList;
import java.util.List;

import fr.univnantes.alma.commons.road.BuildableRoadArea;
import fr.univnantes.alma.commons.town.BuildableTownArea;
import fr.univnantes.alma.core.card.type.DevelopmentCard;
import fr.univnantes.alma.core.card.type.SpecialCard;
import fr.univnantes.alma.core.board.Board;
import fr.univnantes.alma.core.buildArea.BuildableArea;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.commons.road.Road;
import fr.univnantes.alma.commons.territory.Territory;

public class Territory {
    private final List<BuildableTownArea> buildableTown;
    private final List<BuildableRoadArea> buildableRoads;
    private final Resource ressource;
    private final Integer numberAssociated;
    private boolean thiefOccupation;

    public Territory(List<BuildableTownArea> buildableTown, List<BuildableRoadArea> buildableRoads, Resource ressource, Integer numberAssociated) {
        this.buildableTown = buildableTown;
        this.buildableRoads = buildableRoads;
        this.ressource = ressource;
        this.numberAssociated = numberAssociated;
        this.thiefOccupation = this.ressource.getClass().getName().equals("None");
    }

    public List<BuildableTownArea> getBuildableTown() {
        return buildableTown;
    }

    public List<BuildableRoadArea> getBuildableRoads() {
        return buildableRoads;
    }

    public Resource getRessource() {
        return ressource;
    }

    public Integer getNumberAssociated(){
        return numberAssociated;
    }

    public void setThiefOccupation(boolean thiefOccupation) {
        this.thiefOccupation = thiefOccupation;
    }

    public void distributeRessources(){
        if(thiefOccupation)
            return;
        for (BuildableTownArea bta: buildableTown) {
            if(bta.getTown() != null){
                bta.getTown().giveRessource(ressource);
            }
        }
    }
}
