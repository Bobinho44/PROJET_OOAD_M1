package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.core.construction.constructableArea.ConstructableArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Road;
import fr.univnantes.alma.core.ressource.Resource;
import fr.univnantes.alma.core.token.Token;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a territory
 */
public interface Territory {

    /**
     * Gets uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets all neighbour buildings
     *
     * @return all neighbour buildings
     */
    @NonNull List<ConstructableArea<Building>> getNeighbourBuildings();

    /**
     * Gets all neighbour roads
     *
     * @return all neighbour roads
     */
    @NonNull List<ConstructableArea<Road>> getNeighbourRoads();

    /**
     * Gets the resource
     *
     * @return the optional resource
     */
    @NonNull Optional<Resource> getResource();

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