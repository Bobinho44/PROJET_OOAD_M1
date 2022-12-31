package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.exception.UndefinedTradeReceiverException;
import fr.univnantes.alma.core.exception.UnregisteredTradeException;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.trade.ITrade;
import fr.univnantes.alma.core.trade.ITradeJSON;
import fr.univnantes.alma.core.trade.ITradeManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of a trade manager
 */
public class TradeManager implements ITradeManager {

    /**
     * Fields
     */
    private final Map<UUID, ITrade> trades = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ITrade getTrade(@NonNull ITradeJSON tradeJSON) throws UnregisteredTradeException {
        Objects.requireNonNull(tradeJSON, "tradeJSON cannot be null!");

        return Optional.ofNullable(trades.get(tradeJSON.getUUID())).orElseThrow(UnregisteredTradeException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrade(@NonNull ITradeJSON tradeJSON) {
        Objects.requireNonNull(tradeJSON, "tradeJSON cannot be null!");

        return Optional.ofNullable(trades.get(tradeJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createTrade(@NonNull UUID uuid, @NonNull IPlayer sender, @Nullable IPlayer receiver, @NonNull List<IResource> offer, @NonNull List<IResource> request) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(sender, "sender cannot be null!");
        Objects.requireNonNull(offer, "offer cannot be null!");
        Objects.requireNonNull(request, "request cannot be null!");

        trades.put(uuid, new Trade(uuid, sender, receiver, offer, request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTrade(@NonNull ITrade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        trades.remove(trade.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasReceiver(@NonNull ITrade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getReceiver().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IPlayer getSender(@NonNull ITrade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getSender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IPlayer getReceiver(@NonNull ITrade trade) throws UndefinedTradeReceiverException {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getReceiver().orElseThrow(UndefinedTradeReceiverException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResource> getOffer(@NonNull ITrade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getOffer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResource> getRequest(@NonNull ITrade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getRequest();
    }

}