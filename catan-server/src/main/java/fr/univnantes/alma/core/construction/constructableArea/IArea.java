package fr.univnantes.alma.core.construction.constructableArea;

import fr.univnantes.alma.core.construction.lootStrategy.ILootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.construction.IConstruction;
import fr.univnantes.alma.core.construction.constructStrategy.IConstructStrategy;
import fr.univnantes.alma.core.construction.dock.IDock;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing an area
 */
public interface IArea<T extends IConstruction> {

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
    @NonNull List<IArea<Building>> getNeighbourBuildings();

    /**
     * Gets all neighbour paths
     *
     * @return all neighbour paths
     */
    @NonNull List<IArea<Path>> getNeighbourPaths();

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
    @NonNull
    IConstructStrategy<T> getConstructStrategy();

    /**
     * Sets the construction strategy
     *
     * @param constructStrategy the construction strategy
     */
    void setConstructStrategy(@NonNull IConstructStrategy<T> constructStrategy);

    /**
     * Gets the loot strategy
     *
     * @return the loot strategy
     */
    @NonNull
    ILootStrategy<T> getLootStrategy();

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
    void setLootStrategy(@NonNull ILootStrategy<T> lootStrategy);

    /**
     * Gets the dock
     *
     * @return the optional dock
     */
    @NonNull Optional<IDock> getDock();

}