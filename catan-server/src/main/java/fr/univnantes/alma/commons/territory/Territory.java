package fr.univnantes.alma.commons.territory;

import java.util.*;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.territory.ITerritory;
import fr.univnantes.alma.core.token.IToken;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Implementation of a territory
 */
public abstract class Territory implements ITerritory {

    /**
     * Fields
     */
    private final UUID uuid;
    private final List<IArea<Building>> neighbourBuildings = new ArrayList<>();
    private final List<IArea<Path>> neighbourRoads = new ArrayList<>();
    private final IResource resource;
    private IToken token;
    private boolean hasThief;

    /**
     * Creates a new territory
     *
     * @param resource the resource (null for desert)
     */
    public Territory(@Nullable IResource resource) {
        this.uuid = UUID.randomUUID();
        this.resource = resource;
        this.hasThief = resource == null;
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
    public @NonNull List<IArea<Building>> getNeighbourBuildings() {
        return neighbourBuildings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IArea<Path>> getNeighbourRoads() {
        return neighbourRoads;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<IResource> getResource() {
        return Optional.ofNullable(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull IToken getToken() {
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setToken(@NonNull IToken token) {
        Objects.requireNonNull(token, "token cannot be null!");

        this.token = token;
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
    public void setThiefOccupation(boolean hasThief) {
        this.hasThief = hasThief;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Territory territory)) return false;
        return Objects.equals(uuid, territory.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}