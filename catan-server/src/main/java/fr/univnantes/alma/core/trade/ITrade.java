package fr.univnantes.alma.core.trade;

import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a resource
 */
public interface ITrade {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the sender
     *
     * @return the sender
     */
    @NonNull
    IPlayer getSender();

    /**
     * Gets the receiver
     *
     * @return the optional receiver
     */
    @NonNull Optional<IPlayer> getReceiver();

    /**
     * Gets the offer
     *
     * @return the offer
     */
    @NonNull List<IResource> getOffer();

    /**
     * Gets the request
     *
     * @return the request
     */
    @NonNull List<IResource> getRequest();

}