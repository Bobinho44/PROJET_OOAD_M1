package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.commons.territory.type.PositionConstructableArea;
import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.token.Token;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Territory {

    /**
     * Gets all neighbour buildings
     *
     * @return all neighbour buildings
     */
    @NonNull
    Map<PositionConstructableArea,ConstructableArea<Building>> getNeighbourBuildings();

    /**
     * Checks if the territory has neighbour buildings
     *
     * @return true if the territory has neighbour buildings, false otherwise
     */
    boolean hasNeighbourBuilding();

    /**
     * Adds a neighbour building to the territory
     *
     * @param building the building (constructableArea with a building)
     */
    void addNeighbourBuilding(@NonNull ConstructableArea<Building> building,PositionConstructableArea pca);

    /**
     * Removes a neighbour building from the territory
     *
     * @param building the building (constructableArea with a building)
     */
    void removeNeighbourBuilding(@NonNull ConstructableArea<Building> building);

    /**
     * Gets all neighbour roads
     *
     * @return all neighbour roads
     */
    @NonNull List<ConstructableArea<Road>> getNeighbourRoads();

    /**
     * Checks if the territory has neighbour roads
     *
     * @return true if the territory has neighbour roads, false otherwise
     */
    boolean hasNeighbourRoad();

    /**
     * Adds a neighbour road to the territory
     *
     * @param road the road (constructableArea with a road)
     */
    void addNeighbourRoad(@NonNull ConstructableArea<Road> road);

    /**
     * Removes a neighbour road from the territory
     *
     * @param road the building (constructableArea with a road)
     */
    void removeNeighbourRoad(@NonNull ConstructableArea<Road> road);

    /**
     * Gets the resource
     *
     * @return the optional resource
     */
    @NonNull Optional<Resource> getResource();

    /**
     * Checks if the territory has resource
     *
     * @return true if the territory has resource, false otherwise
     */
    boolean hasResource();

    /**
     * Gets the token
     *
     * @return the token
     */
    @NonNull Token getToken();

    /**
     * Sets the token
     *
     * @param token the token
     */
    void setToken(@NonNull Token token);

    /**
     * Checks if the territory has the thief
     *
     * @return true if the territory has the thief, false otherwise
     */
    boolean hasThief();

    /**
     * Sets the thief occupation status
     *
     * @param hasThief  the thief occupation status
     */
    void setThiefOccupation(boolean hasThief);

}