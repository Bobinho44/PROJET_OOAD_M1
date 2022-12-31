package fr.univnantes.alma.core.construction.constructableArea;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Interface representing a constructable area JSON information
 */
public interface IAreaJSON {

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @NonNull
    UUID getUUID();

    /**
     * Gets the type
     *
     * @return the type
     */
    @NonNull String getType();

}