package fr.univnantes.alma.commons.territory;

import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.core.exception.UndefinedTerritoryResourceException;
import fr.univnantes.alma.core.territory.ITerritoryJSON;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a territory JSON information
 */
public class TerritoryJSON implements ITerritoryJSON {

    /**
     * Fields
     */
    private final UUID uuid;
    private final boolean hasThief;
    private IResourceJSON resource;

    /**
     * Creates a new territory json
     *
     * @param uuid the uuid
     * @param hasThief the presence of the thief
     */
    public TerritoryJSON(@NonNull UUID uuid, boolean hasThief) {
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
    public @NonNull IResourceJSON getResource() throws UndefinedTerritoryResourceException {
        return Optional.ofNullable(resource).orElseThrow(UndefinedTerritoryResourceException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ITerritoryJSON resource(@NonNull IResourceJSON resource) {
        Objects.requireNonNull(resource, "resource cannot be null!");

        this.resource = resource;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof TerritoryJSON territoryJSON)) return false;
        return Objects.equals(uuid, territoryJSON.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}