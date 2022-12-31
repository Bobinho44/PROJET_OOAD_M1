package fr.univnantes.alma.core.territory;

import fr.univnantes.alma.core.resource.IResourceJSON;
import fr.univnantes.alma.core.exception.UndefinedTerritoryResourceException;
import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Interface representing a territory JSON information
 */
public interface ITerritoryJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Checks if the territory has thief
     *
     * @return true if the territory has thief, false otherwise
     */
    boolean hasThief();

    /**
     * Checks if the territory has resource
     *
     * @return true if the territory has resource, false otherwise
     */
    boolean hasResource();

    /**
     * Gets the resource
     *
     * @return the resource
     * @throws UndefinedTerritoryResourceException if the resource does not exist
     */
    @NonNull
    IResourceJSON getResource() throws UndefinedTerritoryResourceException;

    /**
     * Sets the resource
     *
     * @param resource the resource information
     * @return the territory information
     */
    @NonNull
    ITerritoryJSON resource(@NonNull IResourceJSON resource);

}