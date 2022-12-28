package fr.univnantes.alma.core.resource;

import org.springframework.lang.NonNull;

/**
 * Interface representing a resource JSON information
 */
public interface ResourceJSON {

    /**
     * Gets the name
     *
     * @return the name
     */
    @NonNull String getName();

    /**
     * Gets the amount
     *
     * @return the amount
     */
    int getAmount();

}