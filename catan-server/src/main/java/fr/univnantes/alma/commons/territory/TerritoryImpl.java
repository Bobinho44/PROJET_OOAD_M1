package fr.univnantes.alma.commons.territory;

import java.util.*;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.token.Token;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Implementation of a territory
 */
public abstract class TerritoryImpl implements Territory {

    /**
     * Fields
     */
    private final UUID uuid;
    private final List<Area<Building>> neighbourBuildings = new ArrayList<>();
    private final List<Area<Road>> neighbourRoads = new ArrayList<>();
    private final Resource resource;
    private Token token;
    private boolean hasThief;

    /**
     * Creates a new territory
     *
     * @param resource the resource (null for desert)
     */
    public TerritoryImpl(@Nullable Resource resource) {
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
    public @NonNull List<Area<Building>> getNeighbourBuildings() {
        return neighbourBuildings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Area<Road>> getNeighbourRoads() {
        return neighbourRoads;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Token getToken() {
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setToken(@NonNull Token token) {
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
        if (!(o instanceof TerritoryImpl)) return false;

        return uuid.equals(((TerritoryImpl) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}