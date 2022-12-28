package fr.univnantes.alma.commons.construction.constructableArea;

import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.dock.Dock;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Road;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of an area
 */
public class AreaImpl<T extends Construction> implements Area<T> {

    /**
     * Fields
     */
    private final UUID uuid;
    private final List<Area<Building>> neighbourBuildings = new ArrayList<>();
    private final List<Area<Road>> neighbourRoads = new ArrayList<>();
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
    public AreaImpl(@NonNull ConstructStrategy<T> constructStrategy, @NonNull LootStrategy<T> lootStrategy, @Nullable Dock dock) {
        this.uuid = UUID.randomUUID();
        this.constructStrategy = constructStrategy;
        this.lootStrategy = lootStrategy;
        this.dock = dock;
    }

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
    public @NonNull Optional<T> getConstruction(){
        return Optional.ofNullable(construction);
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
    public @NonNull LootStrategy<T> getLootStrategy() {
        return lootStrategy;
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
    public void setLootStrategy(@NonNull LootStrategy<T> lootStrategy) {
        this.lootStrategy = lootStrategy;
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
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof AreaImpl<?>)) return false;

        return uuid.equals(((AreaImpl<?>) o).getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}