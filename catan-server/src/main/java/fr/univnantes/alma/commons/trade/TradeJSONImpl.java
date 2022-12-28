package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.core.player.PlayerJSON;
import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.core.exception.UndefinedTradeReceiverException;
import fr.univnantes.alma.core.trade.TradeJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a trade JSON information
 */
public final class TradeJSONImpl implements TradeJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private final PlayerJSON sender;
    private final PlayerJSON receiver;
    private List<ResourceJSON> offer;
    private List<ResourceJSON> request;

    /**
     * Creates a new trade json
     *
     * @param uuid the uuid
     * @param sender the sender information
     * @param receiver the receiver information
     */
    public TradeJSONImpl(@NonNull UUID uuid, @NonNull PlayerJSON sender, @Nullable PlayerJSON receiver)  {
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
    public TradeJSONImpl(@NonNull UUID uuid, @NonNull PlayerJSON sender)  {
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
    public @NonNull PlayerJSON getSender() {
        return sender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull PlayerJSON getReceiver() throws UndefinedTradeReceiverException {
        return Optional.ofNullable(receiver).orElseThrow(UndefinedTradeReceiverException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ResourceJSON> getOffer() {
        return offer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull TradeJSON offer(@NonNull List<ResourceJSON> offer) {
        Objects.requireNonNull(offer, "offer cannot be null!");

        this.offer = offer;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ResourceJSON> getRequest() {
        return request;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull TradeJSON request(@NonNull List<ResourceJSON> request) {
        Objects.requireNonNull(request, "request cannot be null!");

        this.request = request;

        return this;
    }

}