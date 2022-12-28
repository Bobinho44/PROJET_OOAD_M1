package fr.univnantes.alma.core.construction;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Interface representing a construction JSON information
 */
public interface ConstructionJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull UUID getUUID();

    /**
     * Gets the type
     *
     * @return the type
     */
    @NonNull String getType();

}