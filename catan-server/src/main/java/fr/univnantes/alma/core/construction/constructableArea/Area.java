package fr.univnantes.alma.core.construction.constructableArea;

import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.dock.Dock;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing an area
 */
public interface Area<T extends Construction> {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets all neighbour buildings
     *
     * @return all neighbour buildings
     */
    @NonNull List<Area<Building>> getNeighbourBuildings();

    /**
     * Gets all neighbour roads
     *
     * @return all neighbour roads
     */
    @NonNull List<Area<Road>> getNeighbourRoads();

    /**
     * Gets the construction
     *
     * @return the optional construction
     */
    @NonNull Optional<T> getConstruction();

    /**
     * Sets the construction
     *
     * @param construction the construction
     */
    void setConstruction(@NonNull T construction);

    /**
     * Gets the construction strategy
     *
     * @return the construction strategy
     */
    @NonNull ConstructStrategy<T> getConstructStrategy();

    /**
     * Sets the construction strategy
     *
     * @param constructStrategy the construction strategy
     */
    void setConstructStrategy(@NonNull ConstructStrategy<T> constructStrategy);

    /**
     * Gets the loot strategy
     *
     * @return the loot strategy
     */
    @NonNull LootStrategy<T> getLootStrategy();

    /**
     * Gets the loot amount
     *
     * @return the loot amount
     */
    int getLootAmount();

    /**
     * Sets the loot strategy
     *
     * @param lootStrategy the loot strategy
     */
    void setLootStrategy(@NonNull LootStrategy<T> lootStrategy);

    /**
     * Gets the dock
     *
     * @return the optional dock
     */
    @NonNull Optional<Dock> getDock();

}