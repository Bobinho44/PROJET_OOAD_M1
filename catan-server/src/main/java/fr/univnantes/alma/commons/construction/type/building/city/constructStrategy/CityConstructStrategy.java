package fr.univnantes.alma.commons.construction.type.building.city.constructStrategy;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public class CityConstructStrategy implements ConstructStrategy<Building> {

    /**
     * Singleton
     */
    public static final CityConstructStrategy CITY_CONSTRUCT_STRATEGY = new CityConstructStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct(@NonNull ConstructableArea<Building> area, @NonNull Building building) {
        area.setConstruction(building);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull ConstructableArea<Building> area, @NonNull Building building) {

        if (area.getConstruction().map(build -> !(build instanceof Colony)).orElse(false)) {
            return false;
        }

        if (!area.getNeighbourBuildings().isEmpty()) {
            return false;
        }

        return area.getNeighbourRoads().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(building.getOwner()))
                        .orElse(false));

    }

}