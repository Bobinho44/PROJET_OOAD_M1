package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.core.player.Player;
import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Interface representing a construction
 */
public interface Construction {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the owner
     *
     * @return the owner
     */
    @NonNull Player getOwner();

}