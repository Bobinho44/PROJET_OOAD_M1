package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

public interface Construction {

    /**
     * Gets the owner
     *
     * @return the owner
     */
    @NonNull Player getOwner();

}