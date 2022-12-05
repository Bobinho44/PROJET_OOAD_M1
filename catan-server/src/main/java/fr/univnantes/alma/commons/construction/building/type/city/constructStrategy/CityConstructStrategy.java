package fr.univnantes.alma.commons.construction.building.type.city.constructStrategy;

import fr.univnantes.alma.commons.construction.building.Building;
import fr.univnantes.alma.commons.construction.building.type.colony.Colony;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public class CityConstructStrategy implements ConstructStrategy<Building> {

    public static final CityConstructStrategy CITY_CONSTRUCT_STRATEGY = new CityConstructStrategy();

    @Override
    public void construct(@NonNull ConstructableArea<Building> area, @NonNull Building building) {
        area.setConstruction(building);
    }

    @Override
    public boolean isConstructable(@NonNull ConstructableArea<Building> area, @NonNull Building building) {

        if (area.getConstruction().map(build -> !(build instanceof Colony)).orElse(false)) {
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

}