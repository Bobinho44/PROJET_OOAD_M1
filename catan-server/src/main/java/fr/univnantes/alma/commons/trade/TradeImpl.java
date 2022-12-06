package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.trade.Trade;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public class TradeImpl implements Trade {

    /**
     * Fields
     */
    private final Player sender;
    private final Player receiver;
    private final List<Resource> offer;
    private final List<Resource> request;

    /**
     * Creates a new trade
     *
     * @param sender the sender
     * @param receiver the receiver
     * @param offer the offer
     * @param request the receiver
     */
    public TradeImpl(@NonNull Player sender, @Nullable Player receiver, @NonNull List<Resource> offer, @NonNull List<Resource> request) {
        this.sender = sender;
        this.receiver = receiver;
        this.offer = offer;
        this.request = request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Player getSender() {
        return sender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Player> getReceiver() {
        return Optional.ofNullable(receiver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getOffer() {
        return offer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Resource> getRequest() {
        return request;
    }

}