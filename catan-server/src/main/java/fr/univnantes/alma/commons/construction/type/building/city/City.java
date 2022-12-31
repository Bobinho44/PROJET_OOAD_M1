package fr.univnantes.alma.commons.construction.type.building.city;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.player.IPlayer;
import org.springframework.lang.NonNull;

/**
 * Implementation of a building: city
 */
public class City extends Building {

    /**
     * Creates a new city
     *
     * @param owner the owner
     */
    public City(@NonNull IPlayer owner) {
        super(owner);
    }

}