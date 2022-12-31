package fr.univnantes.alma.commons.construction.type.road.constructStrategy;

import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.construction.constructStrategy.IConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * Implementation of a road construct strategy: roadImpl construct strategy
 */
public class RoadConstructStrategy implements IConstructStrategy<Path> {

    /**
     * Singleton
     */
    public static final RoadConstructStrategy ROAD_CONSTRUCT_STRATEGY = new RoadConstructStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull IArea<Path> area, @NonNull Path path) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(path, "road cannot be null!");

        //Checks if the area has construction
        if (area.getConstruction().isPresent()) {
            return false;
        }

        //Checks if the area jas friendly neighbour road
        return area.getNeighbourPaths().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(path.getOwner()))
                        .orElse(false));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct(@NonNull IArea<Path> area, @NonNull Path path) {
        Objects.requireNonNull(area, "area cannot be null!");
        Objects.requireNonNull(path, "road cannot be null!");

        area.setConstruction(path);
    }

}