package fr.univnantes.alma.commons.construction.area;

import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.dock.IDock;
import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.construction.constructStrategy.IConstructStrategy;
import fr.univnantes.alma.core.construction.lootStrategy.ILootStrategy;
import fr.univnantes.alma.core.construction.type.Path;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Implementation of an area
 */
public class Area<T extends IConstruction> implements IArea<T> {

    /**
     * Fields
     */
    private final UUID uuid;
    private final List<IArea<Building>> neighbourBuildings = new ArrayList<>();
    private final List<IArea<Path>> neighbourRoads = new ArrayList<>();
    private T construction;
    private IConstructStrategy<T> constructStrategy;
    private ILootStrategy<T> lootStrategy;
    private final IDock dock;

    /**
     * Creates a new constructable area
     *
     * @param constructStrategy the construct strategy
     * @param lootStrategy the loot strategy
     * @param dock the dock
     */
    public Area(@NonNull IConstructStrategy<T> constructStrategy, @NonNull ILootStrategy<T> lootStrategy, @Nullable IDock dock) {
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
    public @NonNull List<IArea<Building>> getNeighbourBuildings() {
        return neighbourBuildings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<IArea<Path>> getNeighbourPaths() {
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
    public @NonNull IConstructStrategy<T> getConstructStrategy() {
        return constructStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConstructStrategy(@NonNull IConstructStrategy<T> constructStrategy) {
        this.constructStrategy = constructStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull ILootStrategy<T> getLootStrategy() {
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
    public void setLootStrategy(@NonNull ILootStrategy<T> lootStrategy) {
        this.lootStrategy = lootStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<IDock> getDock() {
        return Optional.ofNullable(dock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@NonNull Object o) {
        if (this == o) return true;
        if (!(o instanceof Area<?> area)) return false;
        return Objects.equals(uuid, area.uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}