package fr.univnantes.alma.core.construction.constructableArea;

import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.construction.Construction;
import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.dock.Dock;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConstructableArea<T extends Construction> {

    @NonNull UUID getUUID();

    /**
     * Gets all neighbour buildings
     *
     * @return all neighbour buildings
     */
    @NonNull List<ConstructableArea<Building>> getNeighbourBuildings();

    /**
     * Checks if the constructable area has neighbour buildings
     *
     * @return true if the constructable area has neighbour buildings, false otherwise
     */
    boolean hasNeighbourBuilding();

    /**
     * Adds the neighbour building to the constructable area
     *
     * @param building the building
     */
    void addNeighbourBuilding(@NonNull ConstructableArea<Building> building);

    /**
     * Removes the neighbour building from the constructable area
     *
     * @param building the building
     */
    void removeNeighbourBuilding(@NonNull ConstructableArea<Building> building);

    /**
     * Gets all neighbour roads
     *
     * @return all neighbour roads
     */
    @NonNull List<ConstructableArea<Road>> getNeighbourRoads();

    /**
     * Checks if the constructable area has neighbour roads
     *
     * @return true if the constructable area has neighbour roads, false otherwise
     */
    boolean hasNeighbourRoad();

    /**
     * Adds the neighbour road to the constructable area
     *
     * @param road the road
     */
    void addNeighbourRoad(@NonNull ConstructableArea<Road> road);

    /**
     * Removes the neighbour road from the constructable area
     *
     * @param road the road
     */
    void removeNeighbourRoad(@NonNull ConstructableArea<Road> road);

    /**
     * Gets the construction
     *
     * @return the optional construction
     */
    @NonNull Optional<T> getConstruction();

    /**
     * Checks if the constructable area has a construction
     *
     * @return true if the constructable area has a construction, false otherwise
     */
    boolean hasConstruction();

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
     * Checks if the construction is constructable
     *
     * @param construction the construction
     * @return true if the construction is constructable, false otherwise
     */
    boolean isConstructable(@NonNull T construction);

    /**
     * Construct the construction
     *
     * @param construction the construction
     */
    void construct(@NonNull T construction);

    /**
     * Sets the loot strategy
     *
     * @param lootStrategy the loot strategy
     */
    void setLootStrategy(@NonNull LootStrategy<T> lootStrategy);

    /**
     * Gets the loot amount
     *
     * @return the loot amount
     */
    int getLootAmount();

    /**
     * Gets the dock
     *
     * @return the optional dock
     */
    @NonNull Optional<Dock> getDock();

    /**
     * Checks if the constructable area has a dock
     *
     * @return true if the constructable area has a dock, false otherwise
     */
    boolean hasDock();

}