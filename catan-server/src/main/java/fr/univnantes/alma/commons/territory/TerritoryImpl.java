package fr.univnantes.alma.commons.territory;

import java.util.*;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.territory.Territory;
import fr.univnantes.alma.core.token.Token;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class TerritoryImpl implements Territory {

    /**
     * Fields
     */
    private final List<ConstructableArea<Building>> neighbourBuildings = new ArrayList<>();
    private final List<ConstructableArea<Road>> neighbourRoads = new ArrayList<>();
    private final Resource resource;
    private Token token;
    private boolean hasThief;

    /**
     * Creates a new territory
     *
     * @param resource the resource (null for desert)
     */
    public TerritoryImpl(@Nullable Resource resource) {
        this.resource = resource;
        this.hasThief = resource == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ConstructableArea<Building>> getNeighbourBuildings() {
        return neighbourBuildings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourBuilding() {
        return !neighbourBuildings.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourBuilding(@NonNull ConstructableArea<Building> building) {
        neighbourBuildings.add(building);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourBuilding(@NonNull ConstructableArea<Building> building) {
        neighbourBuildings.remove(building);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<ConstructableArea<Road>> getNeighbourRoads() {
        return neighbourRoads;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNeighbourRoad() {
        return !neighbourRoads.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNeighbourRoad(@NonNull ConstructableArea<Road> road) {
        neighbourRoads.add(road);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourRoad(@NonNull ConstructableArea<Road> road) {
        neighbourRoads.remove(road);
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
    public boolean hasResource() {
        return resource != null;
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

}