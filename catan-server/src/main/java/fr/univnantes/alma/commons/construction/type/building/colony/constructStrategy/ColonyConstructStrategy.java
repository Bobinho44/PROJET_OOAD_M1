package fr.univnantes.alma.commons.construction.type.building.colony.constructStrategy;

import fr.univnantes.alma.commons.construction.type.building.city.constructStrategy.CityConstructStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a building construct strategy: city construct strategy
 */
public class ColonyConstructStrategy implements ConstructStrategy<Building> {

    /**
     * Singleton
     */
    public static final ColonyConstructStrategy COLONY_CONSTRUCT_STRATEGY = new ColonyConstructStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull Area<Building> area, @NonNull Building building) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(building, "building cannot be null!");

        //Checks if the area has construction
        if (area.getConstruction().isPresent()) {

            return false;
        }

        //Checks if the area has neighbour building
        if (!area.getNeighbourBuildings().isEmpty()) {

            return false;
        }

        //Checks if the area has friendly neighbour road
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

        area.setConstructStrategy(CityConstructStrategy.CITY_CONSTRUCT_STRATEGY);
        area.setConstruction(building);
    }

}