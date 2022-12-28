package fr.univnantes.alma.core.trade;

import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.core.exception.UndefinedTradeReceiverException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a trade JSON information
 */
public interface TradeJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Checks if the trade has receiver
     *
     * @return true if the trade has receiver, false otherwise
     */
    boolean hasReceiver();

    /**
     * Gets the sender
     *
     * @return the sender
     */
    @NonNull PlayerJSON getSender();

    /**
     * Gets the receiver
     *
     * @return the receiver
     * @throws UndefinedTradeReceiverException if the trade does not have receiver
     */
    @NonNull PlayerJSON getReceiver();

    /**
     * Gets the offer
     *
     * @return the offer
     */
    @NonNull List<ResourceJSON> getOffer();

    /**
     * Sets the offer
     *
     * @param offer the offer
     * @return the trade information
     */
    @NonNull TradeJSON offer(@NonNull List<ResourceJSON> offer);

    /**
     * Gets the request
     *
     * @return the request
     */
    List<ResourceJSON> getRequest();

    /**
     * Sets the request
     *
     * @param request the request
     * @return the trade information
     */
    @NonNull TradeJSON request(@NonNull List<ResourceJSON> request);

}
