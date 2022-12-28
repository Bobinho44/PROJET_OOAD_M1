package fr.univnantes.alma.commons.construction.type.building.city.constructStrategy;

import fr.univnantes.alma.commons.construction.type.building.city.City;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.commons.construction.type.building.colony.Colony;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a building construct strategy: city construct strategy
 */
public class CityConstructStrategy implements ConstructStrategy<Building> {

    /**
     * Singleton
     */
    public static final CityConstructStrategy CITY_CONSTRUCT_STRATEGY = new CityConstructStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull Area<Building> area, @NonNull Building building) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(building, "building cannot be null!");

        //Checks if the building is a city
        if (!(building instanceof City)) {
            return false;
        }
        //Checks if the area has a colony
        if (area.getConstruction().map(build -> !(build instanceof Colony)).orElse(true)) {
            return false;
        }

        //Checks if the area has building neighbour
        if (!area.getNeighbourBuildings().isEmpty()) {
            return false;
        }

        //Checks if the area has a friendly neighbour road
        return area.getNeighbourRoads().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(building.getOwner()))
                        .orElse(false));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct(@NonNull Area<Building> area, @NonNull Building building) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(building, "building cannot be null!");

        area.setConstruction(building);
    }

}