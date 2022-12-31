package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.core.construction.constructableArea.IArea;
import fr.univnantes.alma.core.construction.type.Building;
import fr.univnantes.alma.core.construction.type.Path;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.token.IToken;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a territory
 */
public interface ITerritory {

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
    @NonNull List<IArea<Building>> getNeighbourBuildings();

    /**
     * Gets all neighbour roads
     *
     * @return all neighbour roads
     */
    @NonNull List<IArea<Path>> getNeighbourRoads();

    /**
     * Gets the resource
     *
     * @return the optional resource
     */
    @NonNull Optional<IResource> getResource();

    /**
     * Gets the token
     *
     * @return the token
     */
    @NonNull
    IToken getToken();

    /**
     * Sets the token
     *
     * @param token the token
     */
    void setToken(@NonNull IToken token);

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