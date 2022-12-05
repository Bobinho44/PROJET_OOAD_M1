package fr.univnantes.alma.commons.territory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.commons.construction.road.Road;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.token.Token;
import org.springframework.lang.Nullable;

public class Territory {
    private final List<ConstructableArea<Building>> buildableTown = new ArrayList<>();
    private final List<ConstructableArea<Road>> buildableRoads = new ArrayList<>();
    private final Resource resource;
    private Token token;
    private boolean hasThief;

    public Territory(@Nullable Resource resource) {
        this.resource = resource;
        this.hasThief = resource == null;
    }

    public Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public boolean hasThief() {
        return hasThief;
    }

    public List<ConstructableArea<Building>> getBuildableTown() {
        return buildableTown;
    }

    public List<ConstructableArea<Road>> getBuildableRoads() {
        return buildableRoads;
    }

    public void setThiefOccupation(boolean hasThief) {
        this.hasThief = hasThief;
    }

    /**public void distributeResources(){
        if(hasThief)
            return;
        for (BuildableBuildingArea bta: buildableTown) {
            if(bta.getBuilding() != null){
                bta.getBuilding().giveRessource(resource);
            }
        }
    }*/
}
