package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.trade.Trade;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a trade
 */
public class TradeImpl implements Trade {

    /**
     * Fields
     */
    private final UUID uuid;
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
    public TradeImpl(@NonNull UUID uuid, @NonNull Player sender, @Nullable Player receiver, @NonNull List<Resource> offer, @NonNull List<Resource> request) {
        this.uuid = uuid;
        this.sender = sender;
        this.receiver = receiver;
        this.offer = offer;
        this.request = request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull UUID getUUID() {
        return uuid;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeImpl)) return false;

        return Objects.equals(uuid, ((TradeImpl) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}