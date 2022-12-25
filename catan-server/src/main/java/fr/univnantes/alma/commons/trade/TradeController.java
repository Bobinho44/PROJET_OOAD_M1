package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.trade.Trade;
import fr.univnantes.alma.core.trade.TradeManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of a trade manager
 */
public class TradeController implements TradeManager {

    /**
     * Fields
     */
    private final Map<UUID, Trade> trades = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Trade getTrade(@NonNull TradeJSON tradeJSON) throws RuntimeException {
        return Optional.ofNullable(trades.get(tradeJSON.getUUID())).orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTrade(@NonNull TradeJSON tradeJSON) {
        return Optional.ofNullable(trades.get(tradeJSON.getUUID())).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createTrade(@NonNull UUID uuid, @NonNull Player sender, @Nullable Player receiver, @NonNull List<Resource> offer, @NonNull List<Resource> request) {
        trades.put(uuid, new TradeImpl(uuid, sender, receiver, offer, request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTrade(@NonNull Trade trade) {
        trades.remove(trade.getUUID());
    }

    public boolean hasReceiver(@NonNull Trade trade) {
        return trade.getReceiver().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getSender(@NonNull Trade trade) {
        return trade.getSender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getReceiver(@NonNull Trade trade) throws RuntimeException {
        return trade.getReceiver().orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getOffer(@NonNull Trade trade) {
        return trade.getOffer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getRequest(@NonNull Trade trade) {
        return trade.getRequest();
    }

}