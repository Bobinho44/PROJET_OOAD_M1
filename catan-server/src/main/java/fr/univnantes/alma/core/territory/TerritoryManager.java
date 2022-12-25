package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.commons.territory.TerritoryJSON;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.player.Player;
import fr.univnantes.alma.core.ressource.Resource;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * Interface representing a territory manager
 */
public interface TerritoryManager {

    /**
     * Gets the territories information
     *
     * @return the territories information
     */
    @NonNull List<TerritoryJSON> getTerritoriesInformation();

    /**
     * Gets all territories
     *
     * @return all territories
     */
    @NonNull List<Territory> getTerritories();

    /**
     * Gets the territory
     *
     * @param territoryJSON the territory information
     * @return the territory
     * @throws RuntimeException if the territory does not exist
     */
    @NonNull Territory getTerritory(@NonNull TerritoryJSON territoryJSON) throws RuntimeException;

    /**
     * Checks if the territory exist
     *
     * @param territoryJSON the territory information
     * @return true if the territory exist, false otherwise
     */
    boolean hasTerritory(@NonNull TerritoryJSON territoryJSON);

    /**
     * Checks if the territory has neighbour buildings
     *
     * @param territory the territory
     * @param constructableArea the constructable area
     * @return true if the territory has neighbour buildings, false otherwise
     */
    boolean hasNeighbourBuilding(@NonNull Territory territory, @NonNull ConstructableArea<Building> constructableArea);

    /**
     * Adds a neighbour building to the territory
     *
     * @param territory the territory
     * @param building the building (constructableArea with a building)
     */
    void addNeighbourBuilding(@NonNull Territory territory, @NonNull ConstructableArea<Building> building);

    /**
     * Removes a neighbour building from the territory
     *
     * @param territory the territory
     * @param building the building (constructableArea with a building)
     */
    void removeNeighbourBuilding(@NonNull Territory territory, @NonNull ConstructableArea<Building> building);

    /**
     * Checks if the territory has neighbour roads
     *
     * @param territory the territory
     * @param constructableArea the constructable area
     * @return true if the territory has neighbour roads, false otherwise
     */
    boolean hasNeighbourRoad(@NonNull Territory territory, @NonNull ConstructableArea<Road> constructableArea);

    /**
     * Adds a neighbour road to the territory
     *
     * @param territory the territory
     * @param road the road (constructableArea with a road)
     */
    void addNeighbourRoad(@NonNull Territory territory, @NonNull ConstructableArea<Road> road);

    /**
     * Removes a neighbour road from the territory
     *
     * @param territory the territory
     * @param road the building (constructableArea with a road)
     */
    void removeNeighbourRoad(@NonNull Territory territory, @NonNull ConstructableArea<Road> road);

    /**
     * Checks if the territory has resource
     *
     * @param territory the territory
     * @return true if the territory has resource, false otherwise
     */
    boolean hasResource(@NonNull Territory territory);

    /**
     * Gets the territory resource
     *
     * @param territory the territory
     * @return the territory resource
     * @throws RuntimeException if the resource does not exist
     */
    @NonNull Resource getResource(@NonNull Territory territory) throws RuntimeException;

    /**
     * Moves the thief
     *
     * @param territory the territory
     */
    void moveThief(@NonNull Territory territory);

    /**
     * Gets all territories loot
     * @param number the token number
     *
     * @return all territories loot
     */
    @NonNull List<Map.Entry<Player, Resource>> getLoot(int number);

}