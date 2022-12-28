package fr.univnantes.alma.commons.construction.type.road;

import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

/**
 * Implementation of a road: roadImpl
 */
public class RoadImpl extends Road {

    /**
     * Creates a new road
     *
     * @param owner the owner
     */
    public RoadImpl(@NonNull Player owner) {
        super(owner);
    }

}