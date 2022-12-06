package fr.univnantes.alma.commons.construction.type.road.constructStrategy;

import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public class RoadConstructStrategy implements ConstructStrategy<Road> {

    /**
     * Singleton
     */
    public static final RoadConstructStrategy ROAD_CONSTRUCT_STRATEGY = new RoadConstructStrategy();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull ConstructableArea<Road> area, @NonNull Road road) {
        if (area.getConstruction().isPresent()) {
            return false;
        }

        return area.getNeighbourRoads().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(road.getOwner()))
                        .orElse(false));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct(@NonNull ConstructableArea<Road> area, @NonNull Road road) {
        area.setConstruction(road);
    }

}