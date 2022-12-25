package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.commons.resource.ResourceJSON;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.UUID;

public class TerritoryJSON {

    private final UUID uuid;
    private final boolean hasThief;

    private ResourceJSON resource;

    public TerritoryJSON(@NonNull UUID uuid, boolean hasThief) {
        this.uuid = uuid;
        this.hasThief = hasThief;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean hasThief() {
        return hasThief;
    }

    public ResourceJSON getResource() {
        return resource;
    }

    public boolean hasResource() {
        return resource != null;
    }

    public TerritoryJSON resource(@NonNull ResourceJSON resource) {
        this.resource = resource;

        return this;
    }
}
