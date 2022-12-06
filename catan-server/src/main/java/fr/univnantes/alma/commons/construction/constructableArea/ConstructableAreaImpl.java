package fr.univnantes.alma.commons.construction.constructableArea;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.dock.Dock;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Road;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstructableAreaImpl<T extends Construction> implements ConstructableArea<T> {

    /**
     * Fields
     */
    private final List<ConstructableArea<Building>> neighbourBuildings = new ArrayList<>();
    private final List<ConstructableArea<Road>> neighbourRoads = new ArrayList<>();
    private T construction;
    private ConstructStrategy<T> constructStrategy;
    private LootStrategy<T> lootStrategy;
    private final Dock dock;

    /**
     * Creates a new constructable area
     *
     * @param constructStrategy the construct strategy
     * @param lootStrategy the loot strategy
     * @param dock the dock
     */
    public ConstructableAreaImpl(@NonNull ConstructStrategy<T> constructStrategy, @NonNull LootStrategy<T> lootStrategy, @Nullable Dock dock) {
        this.constructStrategy = constructStrategy;
        this.lootStrategy = lootStrategy;
        this.dock = dock;
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
    public @NonNull Optional<T> getConstruction(){
        return Optional.ofNullable(construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasConstruction() {
        return construction != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConstruction(@NonNull T construction) {
        this.construction = construction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ConstructStrategy<T> getConstructStrategy() {
        return constructStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConstructStrategy(@NonNull ConstructStrategy<T> constructStrategy) {
        this.constructStrategy = constructStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConstructable(@NonNull T construction) {
        return constructStrategy.isConstructable(this, construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct(@NonNull T construction) {
        constructStrategy.construct(this, construction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLootStrategy(@NonNull LootStrategy<T> lootStrategy) {
        this.lootStrategy = lootStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLootAmount() {
        return lootStrategy.getLootAmount(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<Dock> getDock() {
        return Optional.ofNullable(dock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDock() {
        return dock != null;
    }

}