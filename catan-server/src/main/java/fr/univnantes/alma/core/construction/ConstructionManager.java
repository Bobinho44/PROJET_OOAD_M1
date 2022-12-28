package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.core.construction.constructStrategy.ConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.AreaJSON;
import fr.univnantes.alma.core.construction.constructableArea.Area;
import fr.univnantes.alma.core.construction.dock.Dock;
import fr.univnantes.alma.core.construction.lootStrategy.LootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.exception.*;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.resource.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Interface representing a construction manager
 */
public interface ConstructionManager {

    /**
     * Gets the constructable areas information
     *
     * @return the constructable areas information
     */
    @NonNull List<AreaJSON> getAreasInformation();

    /**
     * Gets the area
     *
     * @param areaJSON the area information
     * @param type the type
     * @return the area
     * @throws UnregisteredAreaException if the area does not exist
     */
    @NonNull <T extends Construction> Area<T> getArea(@NonNull AreaJSON areaJSON, @NonNull Class<T> type) throws UnregisteredAreaException;

    /**
     * Checks if the area exist
     *
     * @param areaJSON the area information
     * @param type the type
     * @return true if the area exist, false otherwise
     */
    <T extends Construction> boolean hasArea(@NonNull AreaJSON areaJSON, @NonNull Class<T> type);

    /**
     * Creates the area
     *
     * @param constructStrategy the construct strategy
     * @param lootStrategy the loot strategy
     * @param dock the dock
     */
    <T extends Construction> void createArea(@NonNull ConstructStrategy<T> constructStrategy, @NonNull LootStrategy<T> lootStrategy, @Nullable Dock dock);

    /**
     * Deletes the area
     *
     * @param area the area
     */
    <T extends Construction> void deleteArea(@NonNull Area<T> area);

    /**
     * Checks if the area is owned by the player
     *
     * @param area the area
     * @param player the player
     * @return true if the area is owned by the player, false otherwise
     */
    <T extends Construction> boolean isAreaOwnedByPlayer(@NonNull Area<T> area, @NonNull Player player);

    /**
     * Checks if the area has neighbour building
     *
     * @param area the area
     * @return true if the area has neighbour building, false otherwise
     */
    <T extends Construction> boolean areaHasNeighbourBuilding(@NonNull Area<T> area);

    /**
     * Checks if the area has the building area like neighbour building
     *
     * @param area the area
     * @param buildingArea the building area
     * @return true if the area has the building area like neighbour building, false otherwise
     */
    <T extends Construction> boolean buildingAreaIsAreaNeighbour(@NonNull Area<T> area, @NonNull Area<Building> buildingArea);

    /**
     * Adds the building area to the area
     *
     * @param area the area
     * @param buildingArea the building area
     */
    <T extends Construction> void addNeighbourBuildingToArea(@NonNull Area<T> area, @NonNull Area<Building> buildingArea);

    /**
     * Removes the building area from the area
     *
     * @param area the area
     * @param buildingArea the building area
     */
    <T extends Construction> void removeNeighbourBuildingFromArea(@NonNull Area<T> area, @NonNull Area<Building> buildingArea);

    /**
     * Checks if the area has neighbour road
     *
     * @param area the area
     * @return true if the area has neighbour road, false otherwise
     */
    <T extends Construction> boolean areaHasNeighbourRoad(@NonNull Area<T> area);

    /**
     * Checks if the area has the building area like neighbour building
     *
     * @param area the area
     * @param roadArea the road area
     * @return true if the area has the building area like neighbour building, false otherwise
     */
    <T extends Construction> boolean roadAreaIsAreaNeighbour(@NonNull Area<T> area, @NonNull Area<Road> roadArea);

    /**
     * Adds the road area to the area
     *
     * @param area the area
     * @param roadArea the road area
     */
    <T extends Construction> void addNeighbourRoadToArea(@NonNull Area<T> area, @NonNull Area<Road> roadArea);

    /**
     * Removes the road area from the area
     *
     * @param area the area
     * @param roadArea the road area
     */
    <T extends Construction> void removeNeighbourRoadToArea(@NonNull Area<T> area, @NonNull Area<Road> roadArea);

    /**
     * Gets the construction of the area
     *
     * @param area the area
     * @return the construction
     * @throws UndefinedAreaConstructionException if the area does not have construction
     */
    @NonNull <T extends Construction> Construction getAreaConstruction(@NonNull Area<T> area) throws UndefinedAreaConstructionException;

    /**
     * Checks if the area has construction
     *
     * @param area the area
     * @return true if the area has construction, false otherwise
     */
    <T extends Construction> boolean areaHasConstruction(@NonNull Area<T> area);

    /**
     * Gets the dock of the area
     *
     * @param area the area
     * @return the dock
     * @throws UndefinedAreaDockException if the area does not have dock
     */
    @NonNull <T extends Construction> Dock getAreaDock(@NonNull Area<T> area) throws UndefinedAreaDockException;

    /**
     * Checks if the area has dock
     *
     * @param area the area
     * @return true if the area has dock, false otherwise
     */
    <T extends Construction> boolean areaHasDock(@NonNull Area<T> area);

    /**
     * Gets the resource of the dock
     *
     * @param dock the dock
     * @return the resource
     * @throws UndefinedDockResourceException if the resource of the dock is not set
     */
    @NonNull Resource getDockResource(@NonNull Dock dock) throws UndefinedDockResourceException;

    /**
     * Checks if the dock has resource
     *
     * @param dock the dock
     * @return true if the dock has resource, false otherwise
     */
    boolean dockHasResource(@NonNull Dock dock);

    /**
     * Gets the dock ratio
     *
     * @param dock the dock
     * @return the dock ratio
     */
    int getDockRatio(@NonNull Dock dock);

    /**
     * Checks if the resource is valid for the dock
     *
     * @param dock the dock
     * @param resource the resource
     * @return true if the resource is valid for the dock, false otherwise
     */
    boolean isValidDockResource(@NonNull Dock dock, @NonNull Resource resource);

    /**
     * Checks if the construction is constructable on the area
     *
     * @param area the constructable area
     * @param construction the construction
     * @return true if the construction is constructable on the area, false otherwise
     */
    <T extends Construction> boolean areaIsConstructable(@NonNull Area<T> area, @NonNull T construction);

    /**
     * Constructs the construction on the area
     *
     * @param area the constructable area
     * @param construction the construction
     */
    <T extends Construction> void constructOnArea(@NonNull Area<T> area, @NonNull T construction);

    /**
     * Generates the construction
     *
     * @param constructionJSON the construction information
     * @return the construction
     * @throws InvalidInformationException if the construction information is not valid
     */
    @NonNull <T extends Construction> T generateConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player) throws InvalidInformationException;

    /**
     * Checks if the construction is valid
     *
     * @param constructionJSON the construction
     * @param type the type
     * @param player the owner
     * @return true if the construction is valid, false otherwise
     */
    <T extends Construction> boolean hasConstruction(@NonNull ConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull Player player);

    /**
     * Gets the construction cost
     *
     * @param construction the construction
     * @return the construction cost
     */
    @NonNull List<Resource> getConstructionCost(@NonNull Construction construction);

    /**
     * Gets the minimum dock ratio of the player for the selected resource
     *
     * @param player the player
     * @param resource the resource
     * @return the minimum dock ratio of the player for the selected resource
     */
    int getPlayerDockRatio(@NonNull Player player, @NonNull Resource resource);
}