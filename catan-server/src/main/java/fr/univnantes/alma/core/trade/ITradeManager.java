package fr.univnantes.alma.core.trade;

import fr.univnantes.alma.core.exception.UndefinedTradeReceiverException;
import fr.univnantes.alma.core.exception.UnregisteredTradeException;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Interface representing a trade manager
 */
public interface ITradeManager {

    /**
     * Gets the trade
     *
     * @param tradeJSON the trade information
     * @return the trade
     * @throws UnregisteredTradeException if the trade does not exist
     */
    @NonNull
    ITrade getTrade(@NonNull ITradeJSON tradeJSON) throws UnregisteredTradeException;

    /**
     * Checks if the trade between the sender and the receiver exist
     *
     * @param tradeJSON the trade information
     * @return true if the trade between the sender and the receiver exist, false otherwise
     */
    boolean hasTrade(@NonNull ITradeJSON tradeJSON);

    /**
     * Creates the trade
     *
     * @param uuid the uuid
     * @param sender the sender
     * @param receiver the receiver
     * @param offer the offer
     * @param request the receiver
     */
    void createTrade(@NonNull UUID uuid, @NonNull IPlayer sender, @Nullable IPlayer receiver, @NonNull List<IResource> offer, @NonNull List<IResource> request);


    /**
     * Removes the trade
     *
     * @param trade the trade
     */
    void deleteTrade(@NonNull ITrade trade);

    /**
     * Checks if the trade has receiver
     *
     * @param trade the trade
     * @return true if the trade has receiver, false otherwise
     */
    boolean hasReceiver(@NonNull ITrade trade);

    /**
     * Gets the trade sender
     *
     * @param trade the tade
     * @return the trade sender
     */
    @NonNull
    IPlayer getSender(@NonNull ITrade trade);

    /**
     * Gets the trade receiver
     *
     * @param trade the trade
     * @return the trade receiver
     * @throws UndefinedTradeReceiverException if the trade does not have receiver
     */
    @NonNull
    IPlayer getReceiver(@NonNull ITrade trade) throws UndefinedTradeReceiverException;

    /**
     * Gets the trade offer
     *
     * @param trade the trade
     * @return the trade offer
     */
    @NonNull List<IResource> getOffer(@NonNull ITrade trade);

    /**
     * Gets the trade request
     *
     * @param trade the trade
     * @return the trade request
     */
    @NonNull List<IResource> getRequest(@NonNull ITrade trade);

}