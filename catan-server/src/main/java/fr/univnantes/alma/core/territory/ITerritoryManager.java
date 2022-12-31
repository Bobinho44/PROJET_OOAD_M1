package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.exception.UndefinedTerritoryResourceException;
import fr.univnantes.alma.core.exception.UnregisteredTerritoryException;
import fr.univnantes.alma.core.player.IPlayer;
import fr.univnantes.alma.core.resource.IResource;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Interface representing a territory manager
 */
public interface ITerritoryManager {

    /**
     * Gets the territories information
     *
     * @return the territories information
     */
    @NonNull List<ITerritoryJSON> getTerritoriesInformation();

    /**
     * Gets the territory
     *
     * @param territoryJSON the territory information
     * @return the territory
     * @throws UnregisteredTerritoryException if the trade does not exist
     */
    @NonNull
    ITerritory getTerritory(@NonNull ITerritoryJSON territoryJSON) throws UnregisteredTerritoryException;

    /**
     * Checks if the territory exist
     *
     * @param territoryJSON the territory information
     * @return true if the territory exist, false otherwise
     */
    boolean hasTerritory(@NonNull ITerritoryJSON territoryJSON);

    /**
     * Checks if the territory has neighbour buildings
     *
     * @param territory the territory
     * @param area the constructable area
     * @return true if the territory has neighbour buildings, false otherwise
     */
    boolean hasNeighbourBuilding(@NonNull ITerritory territory, @NonNull IArea<Building> area);

    /**
     * Adds a neighbour building to the territory
     *
     * @param territory the territory
     * @param building the building (constructableArea with a building)
     */
    void addNeighbourBuilding(@NonNull ITerritory territory, @NonNull IArea<Building> building);

    /**
     * Removes a neighbour building from the territory
     *
     * @param territory the territory
     * @param building the building (constructableArea with a building)
     */
    void removeNeighbourBuilding(@NonNull ITerritory territory, @NonNull IArea<Building> building);

    /**
     * Checks if the territory has neighbour paths
     *
     * @param territory the territory
     * @param area the constructable area
     * @return true if the territory has neighbour paths, false otherwise
     */
    boolean hasNeighbourPath(@NonNull ITerritory territory, @NonNull IArea<Path> area);

    /**
     * Adds a neighbour path to the territory
     *
     * @param territory the territory
     * @param path the path (rea with a path)
     */
    void addNeighbourPath(@NonNull ITerritory territory, @NonNull IArea<Path> path);

    /**
     * Removes a neighbour path from the territory
     *
     * @param territory the territory
     * @param path the building (area with a path)
     */
    void removeNeighbourPath(@NonNull ITerritory territory, @NonNull IArea<Path> path);

    /**
     * Checks if the territory has resource
     *
     * @param territory the territory
     * @return true if the territory has resource, false otherwise
     */
    boolean hasResource(@NonNull ITerritory territory);

    /**
     * Gets the territory resource
     *
     * @param territory the territory
     * @return the territory resource
     * @throws UndefinedTerritoryResourceException if the resource does not exist
     */
    @NonNull
    IResource getResource(@NonNull ITerritory territory) throws UndefinedTerritoryResourceException;

    /**
     * Moves the thief
     *
     * @param territory the territory
     */
    void moveThief(@NonNull ITerritory territory);

    /**
     * Gets all territories loot
     * @param number the token number
     *
     * @return all territories loot
     */
    @NonNull List<Map.Entry<IPlayer, IResource>> getLoot(int number);

}