package fr.univnantes.alma.core.trade;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface Trade {

    /**
     * Gets the sender
     *
     * @return the sender
     */
    @NonNull Player getSender();

    /**
     * Gets the receiver
     *
     * @return the optional receiver
     */
    @NonNull Optional<Player> getReceiver();

    /**
     * Gets the offer
     *
     * @return the offer
     */
    @NonNull List<Resource> getOffer();

    /**
     * Gets the request
     *
     * @return the request
     */
    @NonNull List<Resource> getRequest();

}