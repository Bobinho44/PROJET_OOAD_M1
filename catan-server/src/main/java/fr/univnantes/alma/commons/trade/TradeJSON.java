package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.IPlayerJSON;
import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.core.exception.UndefinedTradeReceiverException;
import fr.univnantes.alma.core.trade.ITradeJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a trade JSON information
 */
public class TradeJSON implements ITradeJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private final IPlayerJSON sender;
    private final IPlayerJSON receiver;
    private List<IResourceJSON> offer;
    private List<IResourceJSON> request;

    /**
     * Creates a new trade json
     *
     * @param uuid the uuid
     * @param sender the sender information
     * @param receiver the receiver information
     */
    public TradeJSON(@NonNull UUID uuid, @NonNull IPlayerJSON sender, @Nullable IPlayerJSON receiver)  {
        Objects.requireNonNull(uuid, "uuid cannot be null!");
        Objects.requireNonNull(sender, "sender cannot be null!");
        Objects.requireNonNull(receiver, "receiver cannot be null!");

        this.uuid = uuid;
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Creates a new trade json
     *
     * @param uuid the uuid
     * @param sender the sender information
     */
    public TradeJSON(@NonNull UUID uuid, @NonNull IPlayerJSON sender)  {
        this(uuid, sender, null);
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
    public boolean hasReceiver() {
        return receiver != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IPlayerJSON getSender() {
        return sender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IPlayerJSON getReceiver() throws UndefinedTradeReceiverException {
        return Optional.ofNullable(receiver).orElseThrow(UndefinedTradeReceiverException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IResourceJSON> getOffer() {
        return offer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ITradeJSON offer(@NonNull List<IResourceJSON> offer) {
        Objects.requireNonNull(offer, "offer cannot be null!");

        this.offer = offer;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IResourceJSON> getRequest() {
        return request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ITradeJSON request(@NonNull List<IResourceJSON> request) {
        Objects.requireNonNull(request, "request cannot be null!");

        this.request = request;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeJSON tradeJSON)) return false;
        return Objects.equals(uuid, tradeJSON.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}