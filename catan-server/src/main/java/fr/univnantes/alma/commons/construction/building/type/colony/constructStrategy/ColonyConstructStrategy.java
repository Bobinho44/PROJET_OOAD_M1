package fr.univnantes.alma.commons.construction.building.type.colony.constructStrategy;

import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.commons.construction.building.type.city.constructStrategy.CityConstructStrategy;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public class ColonyConstructStrategy implements ConstructStrategy<Building> {

    public static final ColonyConstructStrategy COLONY_CONSTRUCT_STRATEGY = new ColonyConstructStrategy();

    @Override
    public boolean isConstructable(@NonNull ConstructableArea<Building> area, @NonNull Building building) {

        if (area.getConstruction().isPresent()) {
            return false;
        }

        if (!area.getBuildingNeighbours().isEmpty()) {
            return false;
        }

        return area.getRoadNeighbours().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(building.getOwner()))
                        .orElse(false));

    }

    @Override
    public void construct(@NonNull ConstructableArea<Building> area, @NonNull Building building) {
        area.setConstructStrategy(CityConstructStrategy.CITY_CONSTRUCT_STRATEGY);
        area.setConstruction(building);
    }

}