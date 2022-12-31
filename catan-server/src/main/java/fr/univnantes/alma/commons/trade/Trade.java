package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.trade.ITrade;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a trade
 */
public class Trade implements ITrade {

    /**
     * Fields
     */
    private final UUID uuid;
    private final IPlayer sender;
    private final IPlayer receiver;
    private final List<IResource> offer;
    private final List<IResource> request;

    /**
     * Creates a new trade
     *
     * @param sender the sender
     * @param receiver the receiver
     * @param offer the offer
     * @param request the receiver
     */
    public Trade(@NonNull UUID uuid, @NonNull IPlayer sender, @Nullable IPlayer receiver, @NonNull List<IResource> offer, @NonNull List<IResource> request) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(sender, "sender cannot be null!");
        Objects.requireNonNull(offer, "offer cannot be null!");
        Objects.requireNonNull(request, "request cannot be null!");

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
    public @NonNull IPlayer getSender() {
        return sender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<IPlayer> getReceiver() {
        return Optional.ofNullable(receiver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResource> getOffer() {
        return offer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResource> getRequest() {
        return request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade trade)) return false;
        return Objects.equals(uuid, trade.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}