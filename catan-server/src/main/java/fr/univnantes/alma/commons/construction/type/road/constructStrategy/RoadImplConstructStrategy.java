package fr.univnantes.alma.commons.construction.type.road.constructStrategy;

import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a road construct strategy: roadImpl construct strategy
 */
public class RoadImplConstructStrategy implements ConstructStrategy<Road> {

    /**
     * Singleton
     */
    public static final RoadImplConstructStrategy ROAD_CONSTRUCT_STRATEGY = new RoadImplConstructStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull Area<Road> area, @NonNull Road road) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(road, "road cannot be null!");

        //Checks if the area has construction
        if (area.getConstruction().isPresent()) {
            return false;
        }

        //Checks if the area jas friendly neighbour road
        return area.getNeighbourRoads().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(road.getOwner()))
                        .orElse(false));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct(@NonNull Area<Road> area, @NonNull Road road) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(road, "road cannot be null!");

        area.setConstruction(road);
    }

}