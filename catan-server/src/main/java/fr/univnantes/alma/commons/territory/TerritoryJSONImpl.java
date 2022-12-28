package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.core.resource.ResourceJSON;
import fr.univnantes.alma.core.exception.UndefinedTerritoryResourceException;
import fr.univnantes.alma.core.territory.TerritoryJSON;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a territory JSON information
 */
public final class TerritoryJSONImpl implements TerritoryJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private final boolean hasThief;
    private ResourceJSON resource;

    /**
     * Creates a new territory json
     *
     * @param uuid the uuid
     * @param hasThief the presence of the thief
     */
    public TerritoryJSONImpl(@NonNull UUID uuid, boolean hasThief) {
        Objects.requireNonNull(uuid, "uuid cannot be null!");

        this.uuid = uuid;
        this.hasThief = hasThief;
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
    public boolean hasThief() {
        return hasThief;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasResource() {
        return resource != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ResourceJSON getResource() throws UndefinedTerritoryResourceException {
        return Optional.ofNullable(resource).orElseThrow(UndefinedTerritoryResourceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull TerritoryJSON resource(@NonNull ResourceJSON resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        this.resource = resource;

        return this;
    }

}