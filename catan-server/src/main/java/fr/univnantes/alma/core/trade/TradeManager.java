package fr.univnantes.alma.core.trade;

import fr.univnantes.alma.commons.trade.TradeJSON;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a trade manager
 */
public interface TradeManager {

    /**
     * Gets the trade
     *
     * @param tradeJSON the trade information
     * @return the trade
     * @throws RuntimeException if the trade does not exist
     */
    @NonNull Trade getTrade(@NonNull TradeJSON tradeJSON) throws RuntimeException;

    /**
     * Checks if the trade between the sender and the receiver exist
     *
     * @param tradeJSON the trade information
     * @return true if the trade between the sender and the receiver exist, false otherwise
     */
    boolean hasTrade(@NonNull TradeJSON tradeJSON);

    /**
     * Creates the trade
     *
     * @param uuid the uuid
     * @param sender the sender
     * @param receiver the receiver
     * @param offer the offer
     * @param request the receiver
     */
    void createTrade(@NonNull UUID uuid, @NonNull Player sender, @Nullable Player receiver, @NonNull List<Resource> offer, @NonNull List<Resource> request);


    /**
     * Removes the trade
     *
     * @param trade the trade
     */
    void deleteTrade(@NonNull Trade trade);

    /**
     * Checks if the trade has receiver
     *
     * @param trade the trade
     * @return true if the trade has receiver, false otherwise
     */
    boolean hasReceiver(@NonNull Trade trade);

    /**
     * Gets the trade sender
     *
     * @param trade the tade
     * @return the trade sender
     */
    @NonNull Player getSender(@NonNull Trade trade);

    /**
     * Gets the trade receiver
     *
     * @param trade the trade
     * @return the trade receiver
     * @throws RuntimeException if the receiver does not exist
     */
    @NonNull Player getReceiver(@NonNull Trade trade) throws RuntimeException;

    /**
     * Gets the trade offer
     *
     * @param trade the trade
     * @return the trade offer
     */
    @NonNull List<Resource> getOffer(@NonNull Trade trade);

    /**
     * Gets the trade request
     *
     * @param trade the trade
     * @return the trade request
     */
    @NonNull List<Resource> getRequest(@NonNull Trade trade);

}