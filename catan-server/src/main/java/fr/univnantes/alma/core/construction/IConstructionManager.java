package fr.univnantes.alma.core.construction;

import fr.univnantes.alma.core.construction.constructStrategy.IConstructStrategy;
import fr.univnantes.alma.core.construction.constructableArea.IAreaJSON;
import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.dock.IDock;
import fr.univnantes.alma.core.construction.lootStrategy.ILootStrategy;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.exception.*;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Interface representing a construction manager
 */
public interface IConstructionManager {

    /**
     * Gets the constructable areas information
     *
     * @return the constructable areas information
     */
    @NonNull List<IAreaJSON> getAreasInformation();

    /**
     * Gets the area
     *
     * @param areaJSON the area information
     * @param type the type
     * @return the area
     * @throws UnregisteredAreaException if the area does not exist
     */
    @NonNull <T extends IConstruction> IArea<T> getArea(@NonNull IAreaJSON areaJSON, @NonNull Class<T> type) throws UnregisteredAreaException;

    /**
     * Checks if the area exist
     *
     * @param areaJSON the area information
     * @param type the type
     * @return true if the area exist, false otherwise
     */
    <T extends IConstruction> boolean hasArea(@NonNull IAreaJSON areaJSON, @NonNull Class<T> type);

    /**
     * Creates the area
     *
     * @param constructStrategy the construct strategy
     * @param lootStrategy the loot strategy
     * @param dock the dock
     */
    <T extends IConstruction> void createArea(@NonNull IConstructStrategy<T> constructStrategy, @NonNull ILootStrategy<T> lootStrategy, @Nullable IDock dock);

    /**
     * Deletes the area
     *
     * @param area the area
     */
    <T extends IConstruction> void deleteArea(@NonNull IArea<T> area);

    /**
     * Checks if the area is owned by the player
     *
     * @param area the area
     * @param player the player
     * @return true if the area is owned by the player, false otherwise
     */
    <T extends IConstruction> boolean isAreaOwnedByPlayer(@NonNull IArea<T> area, @NonNull IPlayer player);

    /**
     * Checks if the area has neighbour building
     *
     * @param area the area
     * @return true if the area has neighbour building, false otherwise
     */
    <T extends IConstruction> boolean areaHasNeighbourBuilding(@NonNull IArea<T> area);

    /**
     * Checks if the area has the building area like neighbour building
     *
     * @param area the area
     * @param buildingArea the building area
     * @return true if the area has the building area like neighbour building, false otherwise
     */
    <T extends IConstruction> boolean buildingAreaIsAreaNeighbour(@NonNull IArea<T> area, @NonNull IArea<Building> buildingArea);

    /**
     * Adds the building area to the area
     *
     * @param area the area
     * @param buildingArea the building area
     */
    <T extends IConstruction> void addNeighbourBuildingToArea(@NonNull IArea<T> area, @NonNull IArea<Building> buildingArea);

    /**
     * Removes the building area from the area
     *
     * @param area the area
     * @param buildingArea the building area
     */
    <T extends IConstruction> void removeNeighbourBuildingFromArea(@NonNull IArea<T> area, @NonNull IArea<Building> buildingArea);

    /**
     * Checks if the area has neighbour path
     *
     * @param area the area
     * @return true if the area has neighbour path, false otherwise
     */
    <T extends IConstruction> boolean areaHasNeighbourPath(@NonNull IArea<T> area);

    /**
     * Checks if the area has the building area like neighbour building
     *
     * @param area the area
     * @param pathArea the path area
     * @return true if the area has the building area like neighbour building, false otherwise
     */
    <T extends IConstruction> boolean pathAreaIsAreaNeighbour(@NonNull IArea<T> area, @NonNull IArea<Path> pathArea);

    /**
     * Adds the path area to the area
     *
     * @param area the area
     * @param pathArea the path area
     */
    <T extends IConstruction> void addNeighbourPathToArea(@NonNull IArea<T> area, @NonNull IArea<Path> pathArea);

    /**
     * Removes the path area from the area
     *
     * @param area the area
     * @param pathArea the path area
     */
    <T extends IConstruction> void removeNeighbourPathToArea(@NonNull IArea<T> area, @NonNull IArea<Path> pathArea);

    /**
     * Gets the construction of the area
     *
     * @param area the area
     * @return the construction
     * @throws UndefinedAreaConstructionException if the area does not have construction
     */
    @NonNull <T extends IConstruction> IConstruction getAreaConstruction(@NonNull IArea<T> area) throws UndefinedAreaConstructionException;

    /**
     * Checks if the area has construction
     *
     * @param area the area
     * @return true if the area has construction, false otherwise
     */
    <T extends IConstruction> boolean areaHasConstruction(@NonNull IArea<T> area);

    /**
     * Gets the dock of the area
     *
     * @param area the area
     * @return the dock
     * @throws UndefinedAreaDockException if the area does not have dock
     */
    @NonNull <T extends IConstruction> IDock getAreaDock(@NonNull IArea<T> area) throws UndefinedAreaDockException;

    /**
     * Checks if the area has dock
     *
     * @param area the area
     * @return true if the area has dock, false otherwise
     */
    <T extends IConstruction> boolean areaHasDock(@NonNull IArea<T> area);

    /**
     * Gets the resource of the dock
     *
     * @param dock the dock
     * @return the resource
     * @throws UndefinedDockResourceException if the resource of the dock is not set
     */
    @NonNull
    IResource getDockResource(@NonNull IDock dock) throws UndefinedDockResourceException;

    /**
     * Checks if the dock has resource
     *
     * @param dock the dock
     * @return true if the dock has resource, false otherwise
     */
    boolean dockHasResource(@NonNull IDock dock);

    /**
     * Gets the dock ratio
     *
     * @param dock the dock
     * @return the dock ratio
     */
    int getDockRatio(@NonNull IDock dock);

    /**
     * Checks if the resource is valid for the dock
     *
     * @param dock the dock
     * @param resource the resource
     * @return true if the resource is valid for the dock, false otherwise
     */
    boolean isValidDockResource(@NonNull IDock dock, @NonNull IResource resource);

    /**
     * Checks if the construction is constructable on the area
     *
     * @param area the constructable area
     * @param construction the construction
     * @return true if the construction is constructable on the area, false otherwise
     */
    <T extends IConstruction> boolean areaIsConstructable(@NonNull IArea<T> area, @NonNull T construction);

    /**
     * Constructs the construction on the area
     *
     * @param area the constructable area
     * @param construction the construction
     */
    <T extends IConstruction> void constructOnArea(@NonNull IArea<T> area, @NonNull T construction);

    /**
     * Generates the construction
     *
     * @param constructionJSON the construction information
     * @return the construction
     * @throws InvalidInformationException if the construction information is not valid
     */
    @NonNull <T extends IConstruction> T generateConstruction(@NonNull IConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull IPlayer player) throws InvalidInformationException;

    /**
     * Checks if the construction is valid
     *
     * @param constructionJSON the construction
     * @param type the type
     * @param player the owner
     * @return true if the construction is valid, false otherwise
     */
    <T extends IConstruction> boolean hasConstruction(@NonNull IConstructionJSON constructionJSON, @NonNull Class<T> type, @NonNull IPlayer player);

    /**
     * Gets the construction cost
     *
     * @param construction the construction
     * @return the construction cost
     */
    @NonNull List<IResource> getConstructionCost(@NonNull IConstruction construction);

    /**
     * Gets the minimum dock ratio of the player for the selected resource
     *
     * @param player the player
     * @param resource the resource
     * @return the minimum dock ratio of the player for the selected resource
     */
    int getPlayerDockRatio(@NonNull IPlayer player, @NonNull IResource resource);

}