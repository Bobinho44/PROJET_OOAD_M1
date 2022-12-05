package fr.univnantes.alma.commons.construction.road.constructStrategy;

import fr.univnantes.alma.commons.construction.road.Road;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import org.springframework.lang.NonNull;

public class RoadConstructStrategy implements ConstructStrategy<Road> {

    public static final RoadConstructStrategy ROAD_CONSTRUCT_STRATEGY = new RoadConstructStrategy();

    @Override
    public boolean isConstructable(@NonNull ConstructableArea<Road> area, @NonNull Road road) {
        if (area.getConstruction().isPresent()) {
            return false;
        }

        return area.getRoadNeighbours().stream()
                .anyMatch(roadNeighbour -> roadNeighbour.getConstruction()
                        .map(nearbyRoad -> nearbyRoad.getOwner().equals(road.getOwner()))
                        .orElse(false));

    }

    @Override
    public void construct(@NonNull ConstructableArea<Road> area, @NonNull Road road) {
        area.setConstruction(road);
    }

}