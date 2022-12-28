package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.exception.UndefinedTradeReceiverException;
import fr.univnantes.alma.core.exception.UnregisteredTradeException;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.trade.Trade;
import fr.univnantes.alma.core.trade.TradeJSON;
import fr.univnantes.alma.core.trade.TradeManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of a trade manager
 */
public final class TradeController implements TradeManager {

    /**
     * Fields
     */
    private final Map<UUID, Trade> trades = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Trade getTrade(@NonNull TradeJSON tradeJSON) throws UnregisteredTradeException {
        Objects.requireNonNull(tradeJSON, "tradeJSON cannot be null!");

        return Optional.ofNullable(trades.get(tradeJSON.getUUID())).orElseThrow(UnregisteredTradeException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrade(@NonNull TradeJSON tradeJSON) {
        Objects.requireNonNull(tradeJSON, "tradeJSON cannot be null!");

        return Optional.ofNullable(trades.get(tradeJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createTrade(@NonNull UUID uuid, @NonNull Player sender, @Nullable Player receiver, @NonNull List<Resource> offer, @NonNull List<Resource> request) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(sender, "sender cannot be null!");
        Objects.requireNonNull(offer, "offer cannot be null!");
        Objects.requireNonNull(request, "request cannot be null!");

        trades.put(uuid, new TradeImpl(uuid, sender, receiver, offer, request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTrade(@NonNull Trade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        trades.remove(trade.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasReceiver(@NonNull Trade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getReceiver().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getSender(@NonNull Trade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getSender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getReceiver(@NonNull Trade trade) throws UndefinedTradeReceiverException {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getReceiver().orElseThrow(UndefinedTradeReceiverException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getOffer(@NonNull Trade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getOffer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getRequest(@NonNull Trade trade) {
        Objects.requireNonNull(trade, "trade cannot be null!");

        return trade.getRequest();
    }

}