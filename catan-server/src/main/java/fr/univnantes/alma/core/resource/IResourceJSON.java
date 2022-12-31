package fr.univnantes.alma.core.resource;

import org.springframework.lang.NonNull;

/**
 * Interface representing a resource JSON information
 */
public interface IResourceJSON {

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String name();

    /**
     * Gets the amount
     *
     * @return the amount
     */
    int amount();

}