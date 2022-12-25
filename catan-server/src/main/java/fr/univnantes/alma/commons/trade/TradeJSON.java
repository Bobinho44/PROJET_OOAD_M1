package fr.univnantes.alma.commons.trade;

import fr.univnantes.alma.commons.player.PlayerJSON;
import fr.univnantes.alma.commons.resource.ResourceJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TradeJSON {

    private final UUID uuid;
    private final PlayerJSON sender;
    private final PlayerJSON receiver;
    private List<ResourceJSON> offer;
    private List<ResourceJSON> request;

    public TradeJSON(@NonNull UUID uuid, @NonNull PlayerJSON sender, @Nullable PlayerJSON receiver)  {
        this.uuid = uuid;
        this.sender = sender;
        this.receiver = receiver;
    }

    public TradeJSON(@NonNull UUID uuid, @NonNull PlayerJSON sender)  {
        this(uuid, sender, null);
    }

    public @NonNull UUID getUUID() {
        return uuid;
    }

    public boolean hasReceiver() {
        return receiver != null;
    }
    public @NonNull PlayerJSON getSender() {
        return sender;
    }

    public @NonNull PlayerJSON getReceiver() throws RuntimeException {
        return Optional.ofNullable(receiver).orElseThrow();
    }

    public @NonNull List<ResourceJSON> getOffer() {
        return offer;
    }

    public @NonNull TradeJSON offer(@NonNull List<ResourceJSON> offer) {
        this.offer = offer;

        return this;
    }

    public List<ResourceJSON> getRequest() {
        return request;
    }

    public @NonNull TradeJSON request(@NonNull List<ResourceJSON> request) {
        this.request = request;

        return this;
    }

}
