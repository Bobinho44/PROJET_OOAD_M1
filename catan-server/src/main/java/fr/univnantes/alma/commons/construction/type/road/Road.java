package fr.univnantes.alma.commons.construction.type.road;

import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.player.IPlayer;
import org.springframework.lang.NonNull;

/**
 * Implementation of a road: roadImpl
 */
public class Road extends Path {

    /**
     * Creates a new road
     *
     * @param owner the owner
     */
    public Road(@NonNull IPlayer owner) {
        super(owner);
    }

}